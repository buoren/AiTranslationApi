package nl.vaguely.translation.controller;

import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.dto.TranslationResponse;
import nl.vaguely.translation.mapper.TranslationMapper;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TranslationController.class)
public class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    @MockBean
    private TranslationMapper translationMapper;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Test
    public void testGetAllTranslations() throws Exception {
        // Given
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setSourceContext("Greeting");
        translation.setSourceLanguage("en");
        translation.setTargetLanguage("nl");
        translation.setTargetGeneratedText("Hallo");
        translation.setCreatedAt(LocalDateTime.now());
        translation.setUpdatedAt(LocalDateTime.now());

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
        response.setSourceContext("Greeting");
        response.setSourceLanguage("en");
        response.setTargetLanguage("nl");
        response.setTargetGeneratedText("Hallo");

        when(translationService.findAll()).thenReturn(Arrays.asList(translation));
        when(translationMapper.toResponse(any())).thenReturn(response);

        // When/Then
        mockMvc.perform(get("/api/translations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].sourceText").value("Hello"))
                .andExpect(jsonPath("$[0].targetGeneratedText").value("Hallo"));
    }

    @Test
    public void testGetTranslationById() throws Exception {
        // Given
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setTargetGeneratedText("Hallo");

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
        response.setTargetGeneratedText("Hallo");

        when(translationService.findById(1L)).thenReturn(Optional.of(translation));
        when(translationMapper.toResponse(any())).thenReturn(response);

        // When/Then
        mockMvc.perform(get("/api/translations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sourceText").value("Hello"))
                .andExpect(jsonPath("$.targetGeneratedText").value("Hallo"));
    }

    @Test
    public void testCreateTranslation() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceContext("Greeting");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setSourceContext("Greeting");
        translation.setSourceLanguage("en");
        translation.setTargetLanguage("nl");
        translation.setTargetGeneratedText("Hallo");

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
        response.setSourceContext("Greeting");
        response.setSourceLanguage("en");
        response.setTargetLanguage("nl");
        response.setTargetGeneratedText("Hallo");

        when(translationMapper.toEntity(any())).thenReturn(translation);
        when(translationService.save(any())).thenReturn(translation);
        when(translationMapper.toResponse(any())).thenReturn(response);

        // When/Then
        mockMvc.perform(post("/api/translations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sourceText").value("Hello"))
                .andExpect(jsonPath("$.targetGeneratedText").value("Hallo"));
    }

    @Test
    public void testTranslate() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceContext("Greeting");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        when(translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn("Hallo");

        // When/Then
        mockMvc.perform(post("/api/translations/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hallo"));
    }
} 