package nl.vaguely.translation.mapper;

import org.springframework.stereotype.Component;
import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.dto.TranslationResponse;
import nl.vaguely.translation.model.Translation;

@Component
public class TranslationMapper {
    public Translation toEntity(TranslationRequest request) {
        Translation translation = new Translation();
        translation.setSourceText(request.getSourceText());
        translation.setSourceContext(request.getSourceContext());
        translation.setTargetPromptText(request.getTargetPromptText());
        translation.setSourceLanguage(request.getSourceLanguage());
        translation.setTargetLanguage(request.getTargetLanguage());
        return translation;
    }

    public TranslationResponse toResponse(Translation entity) {
        TranslationResponse response = new TranslationResponse();
        response.setId(entity.getId());
        response.setSourceText(entity.getSourceText());
        response.setSourceContext(entity.getSourceContext());
        response.setTargetPromptText(entity.getTargetPromptText());
        response.setTargetGeneratedText(entity.getTargetGeneratedText());
        response.setTargetValidatedText(entity.getTargetValidatedText());
        response.setSourceLanguage(entity.getSourceLanguage());
        response.setTargetLanguage(entity.getTargetLanguage());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
} 