package nl.vaguely.translation.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
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
} 