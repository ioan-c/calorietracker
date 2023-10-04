package com.mj.calorietracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDateTime;

@ConfigurationProperties(prefix = "git")
public record GitProperties(Build build, Commit commit) {
}

@ConfigurationProperties(prefix = "git.build")
record Build(LocalDateTime time, String version) {
}

@ConfigurationProperties(prefix = "git.commit")
record Commit(Id id) {
}

@ConfigurationProperties(prefix = "git.commit.id")
record Id(String abbrev, String full) {
}
