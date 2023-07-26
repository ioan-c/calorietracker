package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.mj.calorietracker.enums.ExceptionMessages.USERNAME_NOT_FOUND;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public final UUID getIdByUsername(final String username) {
        return userRepository.findUserEntityByUsernameEquals(username)
                .orElseThrow(() -> new ResourceNotFoundException(USERNAME_NOT_FOUND.getText()))
                .getId();
    }
}
