package nl.vaguely.translation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TranslationRequest {
    @NotBlank(message = "Source text is required")
    private String sourceText;

    private String sourceContext;

    @NotBlank(message = "Source language is required")
    @Size(min = 2, max = 5, message = "Source language must be between 2 and 5 characters")
    private String sourceLanguage;

    @NotBlank(message = "Target language is required")
    @Size(min = 2, max = 5, message = "Target language must be between 2 and 5 characters")
    private String targetLanguage;
} 