package com.mj.calorietracker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
@Slf4j
public class VersionService {
    @Value("${git.commit-info.path}")
    private Resource commitInfo;

    public String getCommitId() {
        String commitId;

        try {
            commitId = commitInfo.getContentAsString(Charset.defaultCharset());
        } catch (IOException e) {
            String message = "Could no read commit info file!";
            log.info(message);
            throw new RuntimeException(message);
        }

        return commitId;
    }
}
