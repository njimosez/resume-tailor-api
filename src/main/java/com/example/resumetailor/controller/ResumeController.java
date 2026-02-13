package com.example.resumetailor.controller;

import com.example.resumetailor.dto.ResumeTailorResponse;
import com.example.resumetailor.entity.ResumeDocument;
import com.example.resumetailor.service.ResumeProcessingService;
import com.example.resumetailor.service.ResumeStorageService;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeProcessingService resumeProcessingService;
    private final ResumeStorageService resumeStorageService;

    public ResumeController(ResumeProcessingService resumeProcessingService, ResumeStorageService resumeStorageService) {
        this.resumeProcessingService = resumeProcessingService;
        this.resumeStorageService = resumeStorageService;
    }

    @PostMapping(value = "/tailor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResumeTailorResponse tailorResume(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "jobQuery", required = false) String jobQuery) {
        return resumeProcessingService.process(file, jobQuery);
    }

    @GetMapping("/{id}")
    public ResumeTailorResponse getResume(@PathVariable UUID id) {
        return resumeProcessingService.fetchTailored(id);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadResume(@PathVariable UUID id) {
        ResumeDocument resume = resumeStorageService.getById(id);
        byte[] content = resume.getTailoredText().getBytes(StandardCharsets.UTF_8);
        String filename = "tailored-" + resume.getOriginalFilename();
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(content);
    }
}
