package com.example.resumetailor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "resume_documents")
public class ResumeDocument {

    @Id
    private UUID id;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "extracted_text", nullable = false, columnDefinition = "TEXT")
    private String extractedText;

    @Column(name = "tailored_text", nullable = false, columnDefinition = "TEXT")
    private String tailoredText;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    protected ResumeDocument() {
    }

    public ResumeDocument(UUID id, String originalFilename, String contentType, String extractedText, String tailoredText,
            OffsetDateTime createdAt) {
        this.id = id;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.extractedText = extractedText;
        this.tailoredText = tailoredText;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public String getTailoredText() {
        return tailoredText;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
