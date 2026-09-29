package com.university.campus.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an entry in the system action history log (Stack).
 */
public class ActionLog {
    private String actionType;
    private String description;
    private String affectedStudentId;
    private String timestamp;

    public ActionLog(String actionType, String description, String affectedStudentId) {
        this.actionType = actionType;
        this.description = description;
        this.affectedStudentId = affectedStudentId;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getActionType() {
        return actionType;
    }

    public String getDescription() {
        return description;
    }

    public String getAffectedStudentId() {
        return affectedStudentId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("[%s] Action: %-8s | Student ID: %-8s | Details: %s",
                timestamp, actionType, (affectedStudentId != null ? affectedStudentId : "N/A"), description);
    }
}
