package com.example.resumetailor.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ResumeTailorResponse(
        UUID resumeId,
        String tailoredResume,
        List<JobMatch> jobMatches,
        OffsetDateTime createdAt,
        String downloadUrl) {
}
