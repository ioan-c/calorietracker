package com.mj.calorietracker.service;

import com.mj.calorietracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public final UUID getIdByUsername(final String username) {
        return userRepository.findUserEntityByUsernameEquals(username).getId();
    }
}
