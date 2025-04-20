package nl.vaguely.translation.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "translation")
@Data
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_text", nullable = false)
    private String sourceText;

    @Column(name = "source_context")
    private String sourceContext;

    @Column(name = "target_prompt_text", nullable = false)
    private String targetPromptText;

    @Column(name = "target_generated_text", nullable = false)
    private String targetGeneratedText;

    @Column(name = "target_validated_text")
    private String targetValidatedText;

    @Column(name = "source_language", nullable = false, length = 5)
    private String sourceLanguage;

    @Column(name = "target_language", nullable = false, length = 5)
    private String targetLanguage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
} 