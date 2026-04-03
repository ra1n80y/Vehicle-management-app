package com.godfrey.fleet.audit;

import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AuditLogService implements IAuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public AuditLogService(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    public void log(AuditAction action, String resourceType, Long resourceId, String performedBy) {
        AuditLog auditLog = new AuditLog(
                action,
                resourceType,
                resourceId,
                performedBy,
                LocalDateTime.now()
        );

        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponseDTO> listAuditLogs() {
        return auditLogRepository.findAllByOrderByPerformedAtDesc()
                .stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuditLogResponseDTO getAuditLog(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log not found with id: " + id));

        return auditLogMapper.toResponse(auditLog);
    }
}