package com.example.resumetailor.service;

import com.example.resumetailor.dto.JobMatch;
import com.example.resumetailor.dto.ResumeTailorResponse;
import com.example.resumetailor.entity.ResumeDocument;
import com.example.resumetailor.exception.BadRequestException;
import com.example.resumetailor.service.JobSearchService.JobSearchResult;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeProcessingService {

    private final ResumeTextExtractor resumeTextExtractor;
    private final ResumeTailorService resumeTailorService;
    private final JobSearchService jobSearchService;
    private final ResumeStorageService resumeStorageService;
    private final ResumeVectorService resumeVectorService;

    public ResumeProcessingService(
            ResumeTextExtractor resumeTextExtractor,
            ResumeTailorService resumeTailorService,
            JobSearchService jobSearchService,
            ResumeStorageService resumeStorageService,
            ResumeVectorService resumeVectorService) {
        this.resumeTextExtractor = resumeTextExtractor;
        this.resumeTailorService = resumeTailorService;
        this.jobSearchService = jobSearchService;
        this.resumeStorageService = resumeStorageService;
        this.resumeVectorService = resumeVectorService;
    }

    public ResumeTailorResponse process(MultipartFile file, String jobQuery) {
        String resumeText = resumeTextExtractor.extract(file);

        ResumeTailorService.ValidationResult validation = resumeTailorService.validateResume(resumeText);
        if (!validation.accepted()) {
            throw new BadRequestException("Uploaded document is not a valid resume/CV: " + validation.reason());
        }

        String query = (jobQuery == null || jobQuery.isBlank())
                ? resumeTailorService.buildJobQuery(resumeText)
                : jobQuery;

        JobSearchResult searchResult = jobSearchService.searchJobs(query);
        String tailoredText = resumeTailorService.tailorResume(resumeText, searchResult.combinedContent());

        ResumeDocument document = resumeStorageService.save(
                safeFilename(file.getOriginalFilename()),
                safeContentType(file.getContentType()),
                resumeText,
                tailoredText);

        resumeVectorService.store(document.getId(), tailoredText, document.getOriginalFilename());

        return new ResumeTailorResponse(
                document.getId(),
                tailoredText,
                searchResult.matches(),
                document.getCreatedAt(),
                "/api/resume/" + document.getId() + "/download");
    }

    public ResumeTailorResponse fetchTailored(UUID id) {
        ResumeDocument document = resumeStorageService.getById(id);
        return new ResumeTailorResponse(
                document.getId(),
                document.getTailoredText(),
                List.of(),
                document.getCreatedAt(),
                "/api/resume/" + document.getId() + "/download");
    }

    private String safeFilename(String filename) {
        return filename == null || filename.isBlank() ? "resume.txt" : filename;
    }

    private String safeContentType(String contentType) {
        return contentType == null || contentType.isBlank() ? "application/octet-stream" : contentType;
    }
}
