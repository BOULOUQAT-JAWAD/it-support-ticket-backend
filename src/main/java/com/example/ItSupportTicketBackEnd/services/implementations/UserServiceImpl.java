package com.example.ItSupportTicketBackEnd.services.implementations;

import com.example.ItSupportTicketBackEnd.core.exceptions.DataNotFoundException;
import com.example.ItSupportTicketBackEnd.entities.User;
import com.example.ItSupportTicketBackEnd.repositories.UserRepository;
import com.example.ItSupportTicketBackEnd.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("user not found"));
    }

}
