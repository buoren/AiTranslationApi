package nl.vaguely.translation.dto;

import java.time.LocalDateTime;

public class TranslationResponse {
    private Long id;
    private String sourceText;
    private String sourceContext;
    private String targetPromptText;
    private String targetGeneratedText;
    private String targetValidatedText;
    private String sourceLanguage;
    private String targetLanguage;
    private LocalDateTime createdAt;
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