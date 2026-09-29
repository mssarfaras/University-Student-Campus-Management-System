package com.university.campus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a student service request in the queue.
 */
public class ServiceRequest {
    private static int idCounter = 1000;
    
    private String requestId;
    private String studentId;
    private String requestType;
    private String description;
    private String timestamp;

    public ServiceRequest(String studentId, String requestType, String description) {
        this.requestId = "REQ" + (++idCounter);
        this.studentId = studentId;
        this.requestType = requestType;
        this.description = description;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getDescription() {
        return description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("Request ID: %-8s | Student ID: %-8s | Type: %-20s | Time: %s\n  Details: %s",
                requestId, studentId, requestType, timestamp, description);
    }
}
