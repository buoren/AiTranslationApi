package nl.vaguely.translation.service;

import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.model.Translation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGPTTranslationService implements TranslationProviderService {
    private final OpenAiService openAiService;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.temperature}")
    private double temperature;

    @Value("${openai.api.max-tokens}")
    private int maxTokens;

    @Override
    public Translation generateTranslation(Translation translation) {
        String prompt = String.format(
            "As a careful United Nations translator, use precision and context to translate the following text from %s to %s." +
            "Do not include any output other than the translation. Context: %s\n\nText: %s",
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

        translation.setTargetGeneratedText(response);
        return translation;
    }
} 