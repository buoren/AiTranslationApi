package nl.vaguely.translation.service;

import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTest {

    @Mock
    private TranslationRepository translationRepository;

    @Mock
    private TranslationProviderService translationProviderService;

    @InjectMocks
    private TranslationService translationService;

    @Test
    public void testFindBySourceTextAndSourceLanguageAndTargetLanguage_ExistingTranslation() {
        // Given
        Translation existingTranslation = new Translation();
        existingTranslation.setTargetGeneratedText("Hallo");
        existingTranslation.setTargetValidatedText("Hallo");

        when(translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn(Optional.of(existingTranslation));

        // When
        String result = translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            "Hello", "Greeting", "en", "nl"
        );

        // Then
        assertEquals("Hallo", result);
        verify(translationProviderService, never()).generateTranslation(any());
    }

    @Test
    public void testFindBySourceTextAndSourceLanguageAndTargetLanguage_NewTranslation() {
        // Given
        Translation newTranslation = new Translation();
        newTranslation.setTargetGeneratedText("Hallo");

        when(translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn(Optional.empty());

        when(translationProviderService.generateTranslation(any()))
            .thenReturn(newTranslation);

        // When
        String result = translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            "Hello", "Greeting", "en", "nl"
        );

        // Then
        assertEquals("Hallo", result);
        verify(translationProviderService).generateTranslation(any());
    }
} 