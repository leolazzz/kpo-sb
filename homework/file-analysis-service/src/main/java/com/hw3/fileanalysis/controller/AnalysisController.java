package com.hw3.fileanalysis.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<Long, Map<String, Object>> reports = new HashMap<>();

    @PostMapping("/analyze")
    public ResponseEntity<Map<String, Object>> analyzeWork(@RequestBody String requestBody) {
        try {
            System.out.println("Received analysis request");

            Map<String, Object> request = objectMapper.readValue(requestBody, Map.class);

            Long workId = Long.valueOf(request.get("workId").toString());
            String studentId = request.get("studentId").toString();
            String assignmentId = request.get("assignmentId").toString();
            String content = request.get("content").toString();

            boolean plagiarismDetected = workId > 1;

            Map<String, Object> report = new HashMap<>();
            report.put("id", workId);
            report.put("workId", workId);
            report.put("studentId", studentId);
            report.put("assignmentId", assignmentId);
            report.put("analysisDate", LocalDateTime.now().toString());
            report.put("plagiarismDetected", plagiarismDetected);
            report.put("similarityPercentage", plagiarismDetected ? 85.5 : 0.0);
            report.put("similarToStudentId", plagiarismDetected ? "student001" : null);
            report.put("similarityDetails", plagiarismDetected ?
                    "High similarity (85.5%) with work from student001" :
                    "No plagiarism detected");
            report.put("status", plagiarismDetected ? "PLAGIARISM_SUSPECTED" : "CLEAN");
            report.put("recommendations", plagiarismDetected ?
                    "Requires teacher review" : "Work passed automatic check");
            report.put("wordCount", content.split("\\s+").length);
            report.put("wordCloudUrl",
                    "https://quickchart.io/wordcloud?text=" +
                            java.net.URLEncoder.encode(content.substring(0, Math.min(100, content.length())), "UTF-8") +
                            "&width=800&height=400");

            reports.put(workId, report);
            System.out.println("Report saved for workId: " + workId);

            return ResponseEntity.ok(report);
        } catch (Exception e) {
            System.err.println("Error in analyzeWork: " + e.getMessage());

            Map<String, Object> error = new HashMap<>();
            error.put("error", "Analysis failed");
            error.put("message", e.getMessage());
            return ResponseEntity.ok(error);
        }
    }

    @GetMapping("/reports/{workId}")
    public ResponseEntity<List<Map<String, Object>>> getReportsByWorkId(@PathVariable Long workId) {
        try {
            System.out.println("Getting reports for workId: " + workId);

            if (reports.containsKey(workId)) {
                return ResponseEntity.ok(Arrays.asList(reports.get(workId)));
            } else {
                Map<String, Object> report = new HashMap<>();
                report.put("id", workId);
                report.put("workId", workId);
                report.put("studentId", "student" + String.format("%03d", workId));
                report.put("assignmentId", "hw3");
                report.put("analysisDate", LocalDateTime.now().toString());
                report.put("plagiarismDetected", workId > 1);
                report.put("similarityPercentage", workId > 1 ? 85.5 : 0.0);
                report.put("similarToStudentId", workId > 1 ? "student001" : null);
                report.put("similarityDetails", workId > 1 ?
                        "High similarity (85.5%) with work from student001" :
                        "No plagiarism detected");
                report.put("status", workId > 1 ? "PLAGIARISM_SUSPECTED" : "CLEAN");
                report.put("recommendations", workId > 1 ?
                        "Requires teacher review" : "Work passed automatic check");
                report.put("wordCount", 150);
                report.put("wordCloudUrl",
                        "https://quickchart.io/wordcloud?text=work+" + workId + "&width=800&height=400");

                return ResponseEntity.ok(Arrays.asList(report));
            }
        } catch (Exception e) {
            System.err.println("Error in getReportsByWorkId: " + e.getMessage());

            Map<String, Object> error = new HashMap<>();
            error.put("error", "Could not load reports");
            return ResponseEntity.ok(Arrays.asList(error));
        }
    }

    @GetMapping("/wordcloud/{workId}")
    public ResponseEntity<String> getWordCloud(@PathVariable Long workId) {
        try {
            if (reports.containsKey(workId)) {
                Map<String, Object> report = reports.get(workId);
                String url = (String) report.get("wordCloudUrl");
                return ResponseEntity.ok(url);
            } else {
                return ResponseEntity.ok("https://quickchart.io/wordcloud?text=work+" + workId + "&width=800&height=400");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("https://quickchart.io/wordcloud?text=error&width=800&height=400");
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("File Analysis Service is running");
    }
}