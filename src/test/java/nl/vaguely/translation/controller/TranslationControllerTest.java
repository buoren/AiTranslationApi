package nl.vaguely.translation.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.dto.TranslationResponse;
import nl.vaguely.translation.mapper.TranslationMapper;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.service.TranslationService;

@ExtendWith(MockitoExtension.class)
public class TranslationControllerTest {

    @Mock
    private TranslationService translationService;

    @Mock
    private TranslationMapper translationMapper;

    @InjectMocks
    private TranslationController translationController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(translationController).build();
        
        // Setup default mapper behavior with lenient stubbing
        lenient().when(translationMapper.toResponse(any(Translation.class))).thenAnswer(invocation -> {
            Translation translation = invocation.getArgument(0);
            TranslationResponse response = new TranslationResponse();
            response.setId(translation.getId());
            response.setSourceText(translation.getSourceText());
            response.setTargetGeneratedText(translation.getTargetGeneratedText());
            return response;
        });
    }

    @Test
    public void testGetAllTranslations() throws Exception {
        // Given
        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setTargetGeneratedText("Hallo");

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
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
    public void testGetTranslationById_Existing() throws Exception {
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
    public void testGetTranslationById_NotFound() throws Exception {
        // Given
        when(translationService.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(get("/api/translations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTranslation() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setTargetGeneratedText("Hallo");

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
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
    public void testUpdateTranslation() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        Translation translation = new Translation();
        translation.setId(1L);
        translation.setSourceText("Hello");
        translation.setTargetGeneratedText("Hallo");

        TranslationResponse response = new TranslationResponse();
        response.setId(1L);
        response.setSourceText("Hello");
        response.setTargetGeneratedText("Hallo");

        when(translationService.findById(1L)).thenReturn(Optional.of(translation));
        when(translationMapper.toEntity(any())).thenReturn(translation);
        when(translationService.save(any())).thenReturn(translation);
        when(translationMapper.toResponse(any())).thenReturn(response);

        // When/Then
        mockMvc.perform(put("/api/translations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.sourceText").value("Hello"))
                .andExpect(jsonPath("$.targetGeneratedText").value("Hallo"));
    }

    @Test
    public void testUpdateTranslation_NotFound() throws Exception {
        // Given
        TranslationRequest request = new TranslationRequest();
        request.setSourceText("Hello");
        request.setSourceLanguage("en");
        request.setTargetLanguage("nl");

        when(translationService.findById(1L)).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(put("/api/translations/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTranslation() throws Exception {
        // Given
        Translation translation = new Translation();
        translation.setId(1L);
        when(translationService.findById(1L)).thenReturn(Optional.of(translation));

        // When/Then
        mockMvc.perform(delete("/api/translations/1"))
                .andExpect(status().isOk());
        
        verify(translationService).deleteById(1L);
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
                .andExpect(jsonPath("$.result").value("Hallo"));
    }
} 