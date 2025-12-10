package com.hw3.apigateway.controller;

import com.hw3.apigateway.service.HttpClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class GatewayController {

    private final HttpClientService httpClient;

    public GatewayController(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @PostMapping("/works")
    public ResponseEntity<?> uploadWork(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") String studentId,
            @RequestParam("assignmentId") String assignmentId,
            @RequestParam(value = "studentName", required = false) String studentName) {

        return httpClient.uploadToFileService(file, studentId, assignmentId, studentName);
    }

    @GetMapping("/works/{workId}")
    public ResponseEntity<?> getWork(@PathVariable Long workId) {
        return httpClient.getWorkFromFileService(workId);
    }

    @GetMapping("/works/{workId}/reports")
    public ResponseEntity<?> getWorkReports(@PathVariable Long workId) {
        return httpClient.getReportsFromAnalysisService(workId);
    }

    @GetMapping("/works/{workId}/wordcloud")
    public ResponseEntity<?> getWordCloud(@PathVariable Long workId) {
        return httpClient.getWordCloud(workId);
    }

    @GetMapping("/assignments/{assignmentId}/works")
    public ResponseEntity<?> getWorksByAssignment(@PathVariable String assignmentId) {
        return httpClient.getWorksByAssignment(assignmentId);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API Gateway is running");
    }
}