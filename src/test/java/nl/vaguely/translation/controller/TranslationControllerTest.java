package nl.vaguely.translation.controller;

import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.mapper.TranslationMapper;
import nl.vaguely.translation.service.TranslationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TranslationController.class)
public class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TranslationService translationService;

    @MockBean
    private TranslationMapper translationMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testTranslate() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceContext("Greeting");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        String expectedTranslation = "Hallo";
        when(translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            any(), any(), any(), any()
        )).thenReturn(expectedTranslation);

        // When/Then
        mockMvc.perform(post("/api/translations/translate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedTranslation));
    }
} 