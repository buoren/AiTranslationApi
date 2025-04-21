package nl.vaguely.translation.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "translation")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_text", nullable = false)
    private String sourceText;

    @Column(name = "source_context")
    private String sourceContext;

    @Column(name = "target_prompt_text")
    private String targetPromptText;

    @Column(name = "target_generated_text")
    private String targetGeneratedText;

    @Column(name = "target_validated_text")
    private String targetValidatedText;

    @Column(name = "source_language", nullable = false)
    private String sourceLanguage;

    @Column(name = "target_language", nullable = false)
    private String targetLanguage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSourceText() { return sourceText; }
    public void setSourceText(String sourceText) { this.sourceText = sourceText; }
    public String getSourceContext() { return sourceContext; }
    public void setSourceContext(String sourceContext) { this.sourceContext = sourceContext; }
    public String getTargetPromptText() { return targetPromptText; }
    public void setTargetPromptText(String targetPromptText) { this.targetPromptText = targetPromptText; }
    public String getTargetGeneratedText() { return targetGeneratedText; }
    public void setTargetGeneratedText(String targetGeneratedText) { this.targetGeneratedText = targetGeneratedText; }
    public String getTargetValidatedText() { return targetValidatedText; }
    public void setTargetValidatedText(String targetValidatedText) { this.targetValidatedText = targetValidatedText; }
    public String getSourceLanguage() { return sourceLanguage; }
    public void setSourceLanguage(String sourceLanguage) { this.sourceLanguage = sourceLanguage; }
    public String getTargetLanguage() { return targetLanguage; }
    public void setTargetLanguage(String targetLanguage) { this.targetLanguage = targetLanguage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
} 