package com.mj.calorietracker.controller;

import com.mj.calorietracker.model.ResourceId;
import com.mj.calorietracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResourceId getUserIdByUsername(@PathVariable String username) {
        return new ResourceId(userService.getIdByUsername(username));
    }
}
