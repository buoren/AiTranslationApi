package nl.vaguely.translation.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;

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

        when(translationRepository.save(any()))
            .thenReturn(newTranslation);

        // When
        String result = translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            "Hello", "Greeting", "en", "nl"
        );

        // Then
        assertEquals("Hallo", result);
        verify(translationProviderService).generateTranslation(any());
        verify(translationRepository).save(any());
    }

    @Test
    public void testSave_ExistingTranslation() {
        // Given
        Translation existingTranslation = new Translation();
        existingTranslation.setId(1L);
        existingTranslation.setSourceText("Hello");
        existingTranslation.setSourceContext("Greeting");
        existingTranslation.setSourceLanguage("en");
        existingTranslation.setTargetLanguage("nl");
        existingTranslation.setTargetGeneratedText("Hallo");
        existingTranslation.setTargetPromptText("Translate 'Hello' to Dutch");

        Translation updatedTranslation = new Translation();
        updatedTranslation.setSourceText("Hello");
        updatedTranslation.setSourceContext("Greeting");
        updatedTranslation.setSourceLanguage("en");
        updatedTranslation.setTargetLanguage("nl");
        updatedTranslation.setTargetGeneratedText("Hallo daar");
        updatedTranslation.setTargetPromptText("Translate 'Hello' to Dutch");

        when(translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn(Optional.of(existingTranslation));

        when(translationRepository.save(any())).thenReturn(existingTranslation);

        // When
        Translation result = translationService.save(updatedTranslation);

        // Then
        assertEquals("Hallo daar", result.getTargetGeneratedText());
        verify(translationRepository).save(any());
        verify(translationProviderService, never()).generateTranslation(any());
    }

    @Test
    public void testSave_NewTranslation() {
        // Given
        Translation newTranslation = new Translation();
        newTranslation.setSourceText("Hello");
        newTranslation.setSourceContext("Greeting");
        newTranslation.setSourceLanguage("en");
        newTranslation.setTargetLanguage("nl");

        when(translationRepository.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn(Optional.empty());

        when(translationProviderService.generateTranslation(any())).thenReturn(newTranslation);
        when(translationRepository.save(any())).thenReturn(newTranslation);

        // When
        Translation result = translationService.save(newTranslation);

        // Then
        assertNotNull(result);
        verify(translationRepository).save(any());
        verify(translationProviderService).generateTranslation(any());
    }
} 