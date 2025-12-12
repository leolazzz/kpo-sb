package com.hw3.apigateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalysisRequest {
    @JsonProperty("workId")
    private Long workId;

    @JsonProperty("studentId")
    private String studentId;

    @JsonProperty("assignmentId")
    private String assignmentId;

    @JsonProperty("content")
    private String content;

    public AnalysisRequest() {}

    public AnalysisRequest(Long workId, String studentId, String assignmentId, String content) {
        this.workId = workId;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.content = content;
    }

    public Long getWorkId() { return workId; }
    public void setWorkId(Long workId) { this.workId = workId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getAssignmentId() { return assignmentId; }
    public void setAssignmentId(String assignmentId) { this.assignmentId = assignmentId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}