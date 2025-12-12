package com.hw3.apigateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class HttpClientService {

    private final RestTemplate restTemplate;

    @Value("${file.storing.service.url:http://localhost:8081}")
    private String fileStoringServiceUrl;

    @Value("${file.analysis.service.url:http://localhost:8082}")
    private String fileAnalysisServiceUrl;

    public HttpClientService() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<?> uploadToFileService(MultipartFile file, String studentId,
                                                 String assignmentId, String studentName) {
        String url = fileStoringServiceUrl + "/api/files/upload";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", convertToResource(file));
        body.add("studentId", studentId);
        body.add("assignmentId", assignmentId);
        if (studentName != null) {
            body.add("studentName", studentName);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> work = response.getBody();
                Long workId = ((Number) work.get("id")).longValue();

                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        triggerAnalysis(workId, studentId, assignmentId);
                    } catch (Exception e) {
                        System.err.println("Background analysis failed: " + e.getMessage());
                    }
                }).start();
            }

            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "File service unavailable: " + e.getMessage()));
        }
    }

    private void triggerAnalysis(Long workId, String studentId, String assignmentId) {
        try {
            String url = fileStoringServiceUrl + "/api/files/" + workId + "/content";

            ResponseEntity<String> contentResponse = restTemplate.getForEntity(url, String.class);

            if (contentResponse.getStatusCode().is2xxSuccessful() && contentResponse.getBody() != null) {
                String analysisUrl = fileAnalysisServiceUrl + "/api/analysis/analyze";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                String content = contentResponse.getBody()
                        .replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t");

                String requestBody = String.format(
                        "{\"workId\": %d, \"studentId\": \"%s\", \"assignmentId\": \"%s\", \"content\": \"%s\"}",
                        workId, studentId, assignmentId, content
                );

                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                restTemplate.postForEntity(analysisUrl, requestEntity, Map.class);
                System.out.println("Analysis triggered for workId: " + workId);
            }
        } catch (Exception e) {
            System.err.println("Failed to trigger analysis: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getWorkFromFileService(Long workId) {
        String url = fileStoringServiceUrl + "/api/files/" + workId;

        try {
            return restTemplate.getForEntity(url, Map.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "File service unavailable"));
        }
    }

    public ResponseEntity<?> getReportsFromAnalysisService(Long workId) {
        String url = fileAnalysisServiceUrl + "/api/analysis/reports/" + workId;

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "No reports found"));
            }
        } catch (Exception e) {
            System.err.println("Error getting reports: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Analysis service unavailable: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> getWordCloud(Long workId) {
        String url = fileAnalysisServiceUrl + "/api/analysis/wordcloud/" + workId;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.ok("https://quickchart.io/wordcloud?text=No+word+cloud+available&width=800&height=400");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Analysis service unavailable"));
        }
    }

    public ResponseEntity<?> getWorksByAssignment(String assignmentId) {
        String url = fileStoringServiceUrl + "/api/files/assignment/" + assignmentId;

        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "No works found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "File service unavailable"));
        }
    }

    private Resource convertToResource(MultipartFile file) {
        try {
            return new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert file", e);
        }
    }
}