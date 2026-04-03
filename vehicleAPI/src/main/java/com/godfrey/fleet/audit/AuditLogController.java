package com.godfrey.fleet.audit;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final IAuditLogService auditLogService;

    public AuditLogController(IAuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).AUDIT_READ)")
    public List<AuditLogResponseDTO> listAuditLogs() {
        return auditLogService.listAuditLogs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).AUDIT_READ)")
    public AuditLogResponseDTO getAuditLog(@PathVariable Long id) {
        return auditLogService.getAuditLog(id);
    }
}