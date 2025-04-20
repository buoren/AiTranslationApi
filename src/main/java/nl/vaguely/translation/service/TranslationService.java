package nl.vaguely.translation.service;

import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;
    private final TranslationProviderService translationProviderService;

    public List<Translation> findAll() {
        return translationRepository.findAll();
    }

    public Optional<Translation> findById(Long id) {
        return translationRepository.findById(id);
    }

    @Transactional
    public Translation save(Translation translation) {
        // Check if a similar translation already exists
        Optional<Translation> existingTranslation = translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            translation.getSourceText(),
            translation.getSourceContext(),
            translation.getSourceLanguage(),
            translation.getTargetLanguage()
        );

        if (existingTranslation.isPresent()) {
            // Update the existing translation
            Translation existing = existingTranslation.get();
            existing.setTargetGeneratedText(translation.getTargetGeneratedText());
            existing.setTargetPromptText(translation.getTargetPromptText());
            if (translation.getTargetValidatedText() != null) {
                existing.setTargetValidatedText(translation.getTargetValidatedText());
            }
            return translationRepository.save(existing);
        }

        return translationRepository.save(translation);
    }

    @Transactional
    public void deleteById(Long id) {
        translationRepository.deleteById(id);
    }

    public String findBySourceTextAndSourceLanguageAndTargetLanguage(
        String sourceText,
        String sourceContext,
        String sourceLanguage,
        String targetLanguage
    ) {
        Optional<Translation> translationOpt = translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            sourceText,
            sourceContext,
            sourceLanguage,
            targetLanguage
        );

        Translation translation = null;
        if (translationOpt.isPresent()) {
            translation = translationOpt.get();
        } else {
            translation = new Translation();
            translation.setSourceText(sourceText);
            translation.setSourceContext(sourceContext);
            translation.setSourceLanguage(sourceLanguage);
            translation.setTargetLanguage(targetLanguage);
            translation = translationProviderService.generateTranslation(translation);
            translationRepository.save(translation);
        }
        
        if(translation.getTargetValidatedText() != null) {
            return translation.getTargetValidatedText();
        } else {
            return translation.getTargetGeneratedText();
        }
    }
} 