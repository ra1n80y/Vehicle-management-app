package com.godfrey.fleet.audit;

import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogResponseDTO toResponse(AuditLog auditLog) {
        return new AuditLogResponseDTO(
                auditLog.getId(),
                auditLog.getAction(),
                auditLog.getResourceType(),
                auditLog.getResourceId(),
                auditLog.getPerformedBy(),
                auditLog.getPerformedAt()
        );
    }
}