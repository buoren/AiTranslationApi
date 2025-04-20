package nl.vaguely.translation.mapper;

import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.dto.TranslationResponse;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;
import nl.vaguely.translation.service.TranslationProviderService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TranslationMapper {
    private final TranslationRepository translationRepository;
    private final TranslationProviderService translationProviderService;

    public Translation toEntity(TranslationRequest request) {
        return translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            request.getSourceText(),
            request.getSourceContext(),
            request.getSourceLanguage(),
            request.getTargetLanguage()
        ).orElseGet(() -> {
            Translation translation = new Translation();
            translation.setSourceText(request.getSourceText());
            translation.setSourceContext(request.getSourceContext());
            translation.setSourceLanguage(request.getSourceLanguage());
            translation.setTargetLanguage(request.getTargetLanguage());
            return translationProviderService.generateTranslation(translation);
        });
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