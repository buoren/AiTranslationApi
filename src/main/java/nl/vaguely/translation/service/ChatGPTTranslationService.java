package nl.vaguely.translation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.theokanning.openai.service.OpenAiService;

import nl.vaguely.translation.model.Translation;

@Service
public class ChatGPTTranslationService implements TranslationProviderService {
    private final OpenAiService openAiService;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.temperature}")
    private double temperature;

    @Value("${openai.api.max-tokens}")
    private int maxTokens;

    public ChatGPTTranslationService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public Translation generateTranslation(Translation translation) {
        String prompt = String.format("""
            As a careful United Nations translator, use precision and context to translate the following text from %s to %s.
            Do not include any output other than the translation. Context: %s

            Text: %s""",
            translation.getSourceLanguage(),
            translation.getTargetLanguage(),
            translation.getSourceContext(),
            translation.getSourceText()
        );

        var chatRequest = com.theokanning.openai.completion.chat.ChatCompletionRequest.builder()
            .model(model)
            .messages(List.of(
                new com.theokanning.openai.completion.chat.ChatMessage(
                    "user",
                    prompt
                )
            ))
            .temperature(temperature)
            .maxTokens(maxTokens)
            .build();

        var chatCompletion = openAiService.createChatCompletion(chatRequest);
        var response = chatCompletion.getChoices().get(0).getMessage().getContent().trim();

        Translation result = new Translation();
        result.setId(translation.getId());
        result.setSourceText(translation.getSourceText());
        result.setSourceContext(translation.getSourceContext());
        result.setSourceLanguage(translation.getSourceLanguage());
        result.setTargetLanguage(translation.getTargetLanguage());
        result.setTargetPromptText(prompt);
        result.setTargetGeneratedText(response);
        return result;
    }
} 