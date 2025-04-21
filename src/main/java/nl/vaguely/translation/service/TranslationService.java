package nl.vaguely.translation.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;

@Service
public class TranslationService {
    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);
    private final TranslationRepository translationRepository;
    private final TranslationProviderService translationProviderService;

    @Autowired
    public TranslationService(TranslationRepository translationRepository, TranslationProviderService translationProviderService) {
        this.translationRepository = translationRepository;
        this.translationProviderService = translationProviderService;
    }

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

        // If no existing translation, generate one using the API
        translation = translationProviderService.generateTranslation(translation);
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
        Translation translation = translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            sourceText, sourceContext, sourceLanguage, targetLanguage
        ).orElseGet(() -> {
            Translation newTranslation = new Translation();
            newTranslation.setSourceText(sourceText);
            newTranslation.setSourceContext(sourceContext);
            newTranslation.setSourceLanguage(sourceLanguage);
            newTranslation.setTargetLanguage(targetLanguage);
            newTranslation = translationProviderService.generateTranslation(newTranslation);
            return translationRepository.save(newTranslation);
        });
        
        return translation.getTargetValidatedText() != null ? 
            translation.getTargetValidatedText() : 
            translation.getTargetGeneratedText();
    }
} 