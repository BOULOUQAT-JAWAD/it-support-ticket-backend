package com.example.ItSupportTicketBackEnd.services;


import com.example.ItSupportTicketBackEnd.dtos.responses.AuditLogResponseDTO;

import java.util.List;

public interface AuditLogService {

    List<AuditLogResponseDTO> getAll();
}
