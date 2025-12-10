package com.hw3.filestoring.controller;

import com.hw3.filestoring.model.Work;
import com.hw3.filestoring.model.WorkStatus;
import com.hw3.filestoring.service.FileStorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("studentId") String studentId,
            @RequestParam("assignmentId") String assignmentId,
            @RequestParam(value = "studentName", required = false) String studentName) {

        Work work = fileStorageService.storeFile(file, studentId, assignmentId, studentName);
        return ResponseEntity.ok(toMap(work));
    }

    @GetMapping("/{workId}")
    public ResponseEntity<?> getWork(@PathVariable Long workId) {
        Optional<Work> work = fileStorageService.getWork(workId);
        return work.map(w -> ResponseEntity.ok(toMap(w)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{workId}/content")
    public ResponseEntity<String> getWorkContent(@PathVariable Long workId) {
        try {
            String content = fileStorageService.getFileContent(workId);
            return ResponseEntity.ok(content);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{workId}/download")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable Long workId) {
        try {
            org.springframework.core.io.Resource resource = fileStorageService.loadFileAsResource(workId);
            Optional<Work> workOpt = fileStorageService.getWork(workId);

            String contentType = "application/octet-stream";
            String filename = "work.txt";

            if (workOpt.isPresent()) {
                Work work = workOpt.get();
                filename = work.getFileName() != null ? work.getFileName() : filename;
                if (work.getFileType() != null) {
                    contentType = work.getFileType();
                }
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<?> getWorksByAssignment(@PathVariable String assignmentId) {
        List<Work> works = fileStorageService.getWorksByAssignment(assignmentId);
        List<Map<String, Object>> response = works.stream()
                .map(this::toMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllWorks() {
        List<Work> works = fileStorageService.getAllWorks();
        List<Map<String, Object>> response = works.stream()
                .map(this::toMap)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{workId}/status")
    public ResponseEntity<?> updateWorkStatus(
            @PathVariable Long workId,
            @RequestBody Map<String, String> request) {

        String statusStr = request.get("status");
        try {
            WorkStatus status = WorkStatus.valueOf(statusStr.toUpperCase());
            Work updatedWork = fileStorageService.updateWorkStatus(workId, status);
            return ResponseEntity.ok(toMap(updatedWork));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("File Storing Service is running");
    }

    private Map<String, Object> toMap(Work work) {
        return Map.of(
                "id", work.getId(),
                "studentId", work.getStudentId(),
                "studentName", work.getStudentName() != null ? work.getStudentName() : "",
                "assignmentId", work.getAssignmentId(),
                "fileName", work.getFileName(),
                "fileSize", work.getFileSize(),
                "fileType", work.getFileType(),
                "submissionDate", work.getSubmissionDate(),
                "status", work.getStatus().name()
        );
    }
}