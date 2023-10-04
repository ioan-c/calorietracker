package com.mj.calorietracker.controller;

import com.mj.calorietracker.service.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/version")
public class VersionController {
    private final VersionService versionService;

    @GetMapping("/commit-id")
    public String getCommitId() {
        return versionService.getCommitId();
    }
}
