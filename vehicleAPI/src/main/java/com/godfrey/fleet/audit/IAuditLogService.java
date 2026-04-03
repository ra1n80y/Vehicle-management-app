package com.godfrey.fleet.audit;

import java.util.List;

public interface IAuditLogService {

    List<AuditLogResponseDTO> listAuditLogs();

    AuditLogResponseDTO getAuditLog(Long id);
}