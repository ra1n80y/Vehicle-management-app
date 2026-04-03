package com.godfrey.fleet.audit;

import java.time.LocalDateTime;

public class AuditLogResponseDTO {

    private Long id;
    private AuditAction action;
    private String resourceType;
    private Long resourceId;
    private String performedBy;
    private LocalDateTime performedAt;

    public AuditLogResponseDTO() {
    }

    public AuditLogResponseDTO(
            Long id,
            AuditAction action,
            String resourceType,
            Long resourceId,
            String performedBy,
            LocalDateTime performedAt
    ) {
        this.id = id;
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.performedBy = performedBy;
        this.performedAt = performedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public LocalDateTime getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDateTime performedAt) {
        this.performedAt = performedAt;
    }
}