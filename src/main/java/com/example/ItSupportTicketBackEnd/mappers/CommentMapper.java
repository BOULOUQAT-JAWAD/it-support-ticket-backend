package com.example.ItSupportTicketBackEnd.mappers;

import com.example.ItSupportTicketBackEnd.dtos.requests.CommentRequestDTO;
import com.example.ItSupportTicketBackEnd.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentRequestDTO request);
}
