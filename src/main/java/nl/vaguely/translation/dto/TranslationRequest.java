package nl.vaguely.translation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TranslationRequest {
    @NotBlank
    private String sourceText;
    private String sourceContext;
    private String targetPromptText;
    @NotBlank
    private String sourceLanguage;
    @NotBlank
    private String targetLanguage;

    // Getters and Setters
    public String getSourceText() { return sourceText; }
    public void setSourceText(String sourceText) { this.sourceText = sourceText; }
    public String getSourceContext() { return sourceContext; }
    public void setSourceContext(String sourceContext) { this.sourceContext = sourceContext; }
    public String getTargetPromptText() { return targetPromptText; }
    public void setTargetPromptText(String targetPromptText) { this.targetPromptText = targetPromptText; }
    public String getSourceLanguage() { return sourceLanguage; }
    public void setSourceLanguage(String sourceLanguage) { this.sourceLanguage = sourceLanguage; }
    public String getTargetLanguage() { return targetLanguage; }
    public void setTargetLanguage(String targetLanguage) { this.targetLanguage = targetLanguage; }
} 