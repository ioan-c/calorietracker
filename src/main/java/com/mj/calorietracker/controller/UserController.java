package com.mj.calorietracker.controller;

import com.mj.calorietracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public UUID findDiaryEntriesByUserIdAndDate(@PathVariable String username) {
        return userService.getIdByUsername(username);
    }
}
