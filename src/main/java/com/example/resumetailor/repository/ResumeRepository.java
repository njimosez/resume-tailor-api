package com.example.resumetailor.repository;

import com.example.resumetailor.entity.ResumeDocument;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<ResumeDocument, UUID> {
}
