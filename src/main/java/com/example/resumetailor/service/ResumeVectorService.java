package com.example.resumetailor.service;

import java.util.Map;
import java.util.List;
import java.util.UUID;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class ResumeVectorService {

    private final VectorStore vectorStore;

    public ResumeVectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void store(UUID resumeId, String content, String originalFilename) {
        Document document = new Document(
                resumeId.toString(),
                content,
                Map.of(
                        "resumeId", resumeId.toString(),
                        "filename", originalFilename));
        vectorStore.add(java.util.List.of(document));
    }

    public String retrieveContext(String query, int topK) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(query).withTopK(topK));
        if (documents == null || documents.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (Document document : documents) {
            if (document.getContent() != null && !document.getContent().isBlank()) {
                builder.append(document.getContent()).append("\n\n");
            }
        }
        return builder.toString().trim();
    }
}
