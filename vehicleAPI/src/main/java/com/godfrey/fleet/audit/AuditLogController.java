package com.godfrey.fleet.audit;

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
    public List<AuditLogResponseDTO> listAuditLogs() {
        return auditLogService.listAuditLogs();
    }

    @GetMapping("/{id}")
    public AuditLogResponseDTO getAuditLog(@PathVariable Long id) {
        return auditLogService.getAuditLog(id);
    }
}