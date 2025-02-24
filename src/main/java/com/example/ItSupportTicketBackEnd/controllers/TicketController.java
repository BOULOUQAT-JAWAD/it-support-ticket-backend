package com.example.ItSupportTicketBackEnd.controllers;

import com.example.ItSupportTicketBackEnd.core.enums.TrackingStatus;
import com.example.ItSupportTicketBackEnd.dtos.requests.TicketRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.TicketResponseDTO;
import com.example.ItSupportTicketBackEnd.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
@AllArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<TicketResponseDTO> persist(@RequestBody TicketRequestDTO request) {
        log.info("adding a ticket");
        return ResponseEntity.ok(ticketService.persist(request));
    }

    @GetMapping("/user")
    public ResponseEntity<List<TicketResponseDTO>> getUserTickets(
            @RequestParam(required = false) Long ticketId,
            @RequestParam(required = false) TrackingStatus status) {
        log.info("getting user tickets");
        return ResponseEntity.ok(ticketService.getUserTickets(ticketId, status));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets(
            @RequestParam(required = false) Long ticketId,
            @RequestParam(required = false) TrackingStatus status) {

        log.info("Getting tickets with ticketId: {} and status: {}", ticketId, status);
        List<TicketResponseDTO> tickets = ticketService.getAllTickets(ticketId, status);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDTO> getTicket(@PathVariable long ticketId) {
        log.info("Getting ticket with ticketId: {} ", ticketId);
        TicketResponseDTO ticket = ticketService.getOneTicket(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/update/status/{ticketId}")
    public ResponseEntity<TicketResponseDTO> updateStatus(@PathVariable long ticketId, @RequestParam String status) {
        log.info("changing status of a ticket");
        return ResponseEntity.ok(ticketService.updateStatus(ticketId, status));
    }
}
