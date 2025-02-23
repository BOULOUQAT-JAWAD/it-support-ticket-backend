package com.example.ItSupportTicketBackEnd.controllers;

import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;
import com.example.ItSupportTicketBackEnd.dtos.responses.DefaultResponseDTO;
import com.example.ItSupportTicketBackEnd.services.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<DefaultResponseDTO> persist(@RequestBody CommentRequestDTO request) {
        log.info("adding a comment");
        commentService.persist(request);
        return ResponseEntity.ok(
                DefaultResponseDTO.builder()
                        .message("comment added successfully!")
                        .time(new Date())
                        .status(HttpStatus.OK.getReasonPhrase())
                        .build()
        );
    }
}
