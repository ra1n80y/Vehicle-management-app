package com.godfrey.fleet.audit;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AuditAction action;

    @Column(nullable = false, length = 100)
    private String resourceType;

    @Column(nullable = false)
    private Long resourceId;

    @Column(nullable = false, length = 100)
    private String performedBy;

    @Column(nullable = false)
    private LocalDateTime performedAt;

    public AuditLog() {
    }

    public AuditLog(
            AuditAction action,
            String resourceType,
            Long resourceId,
            String performedBy,
            LocalDateTime performedAt
    ) {
        this.action = action;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.performedBy = performedBy;
        this.performedAt = performedAt;
    }

    public Long getId() {
        return id;
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