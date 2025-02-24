package com.example.ItSupportTicketBackEnd.services.implementations;


import com.example.ItSupportTicketBackEnd.dtos.responses.AuditLogResponseDTO;
import com.example.ItSupportTicketBackEnd.mappers.AuditLogMapper;
import com.example.ItSupportTicketBackEnd.repositories.AuditLogRepository;
import com.example.ItSupportTicketBackEnd.services.AuditLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @Override
    public List<AuditLogResponseDTO> getAll() {
        return auditLogRepository.findAll().stream().map(
                auditLogMapper::toEntity
        ).toList();
    }
}
