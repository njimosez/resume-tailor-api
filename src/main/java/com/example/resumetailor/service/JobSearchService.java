package com.example.resumetailor.service;

import com.example.resumetailor.config.TavilyProperties;
import com.example.resumetailor.dto.JobMatch;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class JobSearchService {

    private final RestClient restClient;

    public JobSearchService(RestClient.Builder builder, TavilyProperties tavilyProperties) {
        this.restClient = builder
                .baseUrl(tavilyProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.apiKey = tavilyProperties.getApiKey();
    }

    private final String apiKey;

    public JobSearchResult searchJobs(String query) {
        TavilySearchRequest request = new TavilySearchRequest(apiKey, query, 5, "advanced", true);
        TavilySearchResponse response = restClient.post()
                .uri("/search")
                .body(request)
                .retrieve()
                .body(TavilySearchResponse.class);

        if (response == null || response.results() == null) {
            return new JobSearchResult(List.of(), "");
        }

        List<JobMatch> matches = new ArrayList<>();
        StringBuilder combinedContent = new StringBuilder();
        for (TavilySearchResult result : response.results()) {
            matches.add(new JobMatch(
                    safe(result.title()),
                    safe(result.url()),
                    safe(result.content()),
                    result.score() == null ? 0.0 : result.score()));
            combinedContent.append(result.title()).append("\n")
                    .append(result.content()).append("\n\n");
        }

        return new JobSearchResult(matches, combinedContent.toString());
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public record JobSearchResult(List<JobMatch> matches, String combinedContent) {
    }

        private record TavilySearchRequest(
                @JsonProperty("api_key") String apiKey,
            @JsonProperty("query") String query,
            @JsonProperty("max_results") int maxResults,
            @JsonProperty("search_depth") String searchDepth,
            @JsonProperty("include_raw_content") boolean includeRawContent) {
    }

    private record TavilySearchResponse(List<TavilySearchResult> results) {
    }

    private record TavilySearchResult(String title, String url, String content, Double score) {
    }
}
