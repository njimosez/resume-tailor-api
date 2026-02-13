package com.example.resumetailor.service;

import com.example.resumetailor.entity.ResumeDocument;
import com.example.resumetailor.exception.NotFoundException;
import com.example.resumetailor.repository.ResumeRepository;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ResumeStorageService {

    private final ResumeRepository resumeRepository;

    public ResumeStorageService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public ResumeDocument save(String originalFilename, String contentType, String extractedText, String tailoredText) {
        ResumeDocument document = new ResumeDocument(
                UUID.randomUUID(),
                originalFilename,
                contentType,
                extractedText,
                tailoredText,
                OffsetDateTime.now());
        return resumeRepository.save(document);
    }

    public ResumeDocument getById(UUID id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resume not found"));
    }
}
