package com.mj.calorietracker.controller;

import com.mj.calorietracker.config.GitProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/version")
public class VersionController {
    private final GitProperties gitProperties;

    @GetMapping
    public GitProperties getVersion() {
        return gitProperties;
    }
}
