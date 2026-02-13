package com.example.resumetailor.service;

import com.example.resumetailor.exception.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.stereotype.Service;

@Service
public class ResumeTailorService {

    private static final Logger logger = LoggerFactory.getLogger(ResumeTailorService.class);

    private final ChatClient chatClient;
    private final ResumeVectorService resumeVectorService;
    private final SimpleLoggerAdvisor simpleLoggerAdvisor;

    public ResumeTailorService(ChatClient.Builder builder, ResumeVectorService resumeVectorService, SimpleLoggerAdvisor simpleLoggerAdvisor) {
        this.chatClient = builder.build();
        this.resumeVectorService = resumeVectorService;
        this.simpleLoggerAdvisor = simpleLoggerAdvisor;
    }

    public String buildJobQuery(String resumeText) {
        int resumeLength = resumeText == null ? 0 : resumeText.length();
        long startTime = System.nanoTime();
        logger.info("Building job query from resume length={}", resumeLength);
        String prompt = """
                You are a resume assistant. Create a concise job search query based on the resume.
                Return only the query string, no extra text.

                Resume:
                %s
                """.formatted(resumeText);
    String response = callAi(prompt, "buildJobQuery");
    long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
    logger.info("Built job query length={} in {} ms", response == null ? 0 : response.length(), elapsedMs);
    return response;
    }

    public boolean isValidResume(String resumeText) {
        return validateResume(resumeText).accepted();
    }

    public ValidationResult validateResume(String resumeText) {
        if (resumeText == null || resumeText.isBlank()) {
            return new ValidationResult(false, "Missing resume content.");
        }
        int resumeLength = resumeText.length();
        long startTime = System.nanoTime();
        logger.info("Validating resume length={}", resumeLength);
        String prompt = """
                You are a resume/CV validator.
                Decide if the input is a resume/CV.

                ACCEPT only if it contains clear resume structure with multiple items like work experience,
                education, skills/projects/certifications, typically including contact info and/or date ranges
                and roles/employers.

                REJECT if it's a cover letter only, job description, essay, invoice, legal document,
                code dump, chat log, or random notes.

                Respond with two lines:
                1) ACCEPT or REJECT
                2) REASON: <short reason, mention missing sections if any>

                Input:
                %s
                """.formatted(resumeText);
        String response = callAi(prompt, "validateResume");
        long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
        logger.info("Resume validation result='{}' in {} ms", response, elapsedMs);
        ParsedValidation parsed = parseValidationResponse(response);
        return new ValidationResult(parsed.accepted, parsed.reason);
    }

    private ParsedValidation parseValidationResponse(String response) {
        if (response == null || response.isBlank()) {
            return new ParsedValidation(false, "Validation failed to return a decision.");
        }
        String[] lines = response.split("\\R", -1);
        String firstLine = lines.length > 0 ? lines[0].trim() : "";
        boolean accepted = firstLine.equalsIgnoreCase("ACCEPT");
        boolean rejected = firstLine.equalsIgnoreCase("REJECT");
        String reason = "";
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.regionMatches(true, 0, "REASON:", 0, "REASON:".length())) {
                reason = trimmed.substring("REASON:".length()).trim();
                break;
            }
        }
        if (reason.isBlank()) {
            if (rejected) {
                reason = "Missing clear resume structure (work experience, education, skills/projects/certifications).";
            } else {
                reason = "Validation did not include a reason.";
            }
        }
        return new ParsedValidation(accepted && !rejected, reason);
    }

    private record ParsedValidation(boolean accepted, String reason) {}

    public record ValidationResult(boolean accepted, String reason) {}

    public String tailorResume(String resumeText, String jobDetails) {
    int resumeLength = resumeText == null ? 0 : resumeText.length();
    int jobDetailsLength = jobDetails == null ? 0 : jobDetails.length();
    long startTime = System.nanoTime();
    logger.info("Tailoring resume with resume length={} job details length={}", resumeLength, jobDetailsLength);
        String context = resumeVectorService.retrieveContext(resumeText + "\n" + jobDetails, 3);
    logger.info("Retrieved context length={} blank={}", context == null ? 0 : context.length(), context == null || context.isBlank());
        String contextBlock = context.isBlank() ? "" : "\nContext from prior tailored resumes:\n" + context + "\n";
        String prompt = """
                You are a resume tailoring assistant. Rewrite the resume to align with the job details.
                Preserve factual accuracy. Use concise bullet points.

                Job details:
                %s
            %s

                Resume:
                %s
            """.formatted(jobDetails, contextBlock, resumeText);
        String response = callAi(prompt, "tailorResume");
        long elapsedMs = (System.nanoTime() - startTime) / 1_000_000;
        logger.info("Tailored resume length={} in {} ms", response == null ? 0 : response.length(), elapsedMs);
        return response;
    }

    private String callAi(String prompt, String operation) {
        try {
            String content = chatClient.prompt(prompt).call().content();
            return content == null ? "" : content.trim();
        } catch (TransientAiException ex) {
            logger.warn("AI transient failure during {}: {}", operation, ex.getMessage());
            throw new ServiceUnavailableException("AI service temporarily unavailable. Please retry.");
        }
    }
}
