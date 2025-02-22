package com.example.ItSupportTicketBackEnd.controllers.exceptionHandler;


import com.example.ItSupportTicketBackEnd.core.exceptions.DataNotFoundException;
import com.example.ItSupportTicketBackEnd.dtos.responses.DefaultResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@ControllerAdvice
@Slf4j
public class RealEstateExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<DefaultResponseDTO> handleException(DataNotFoundException exception) {
        HttpStatus responseStatus;
        responseStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(DefaultResponseDTO.builder()
                .message(exception.getMessage())
                .status(responseStatus.getReasonPhrase())
                .time(new Date())
                .build(),
                responseStatus);
    }

}