package com.example.resumetailor.service;

import com.example.resumetailor.exception.BadRequestException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeTextExtractor {

    private final Tika tika = new Tika();
    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "application/pdf",
            "text/plain",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

    public String extract(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Resume file is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new BadRequestException("Unsupported resume format. Use PDF, TXT, or DOC/DOCX");
        }

        try (InputStream inputStream = file.getInputStream()) {
            String text = tika.parseToString(inputStream);
            if (text == null || text.isBlank()) {
                throw new BadRequestException("Unable to extract text from resume");
            }
            return text;
        } catch (IOException | TikaException ex) {
            throw new BadRequestException("Failed to read resume file");
        }
    }
}
