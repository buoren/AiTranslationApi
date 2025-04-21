package nl.vaguely.translation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.dto.TranslationRequest;
import nl.vaguely.translation.dto.TranslationResponse;
import nl.vaguely.translation.dto.TranslationResult;
import nl.vaguely.translation.mapper.TranslationMapper;
import nl.vaguely.translation.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;
    private final TranslationMapper translationMapper;

    @GetMapping
    public ResponseEntity<List<TranslationResponse>> getAllTranslations() {
        List<TranslationResponse> responses = translationService.findAll().stream()
                .map(translationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationResponse> getTranslationById(@PathVariable Long id) {
        return translationService.findById(id)
                .map(translationMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TranslationResponse> createTranslationEntity(@Valid @RequestBody TranslationRequest request) {        
        var translation = translationService.save(translationMapper.toEntity(request));
        return ResponseEntity.ok(translationMapper.toResponse(translation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TranslationResponse> updateTranslationEntity(
            @PathVariable Long id,
            @Valid @RequestBody TranslationRequest request) {
        if (!translationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        var translation = translationMapper.toEntity(request);
        translation.setId(id);
        return ResponseEntity.ok(translationMapper.toResponse(translationService.save(translation)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslationEntity(@PathVariable Long id) {
        if (!translationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        translationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/translate")
    public ResponseEntity<TranslationResult> translate(@Valid @RequestBody TranslationRequest request) {
        var translationStr = translationService.findBySourceTextAndSourceLanguageAndTargetLanguage(
            request.getSourceText(),
            request.getSourceContext(),
            request.getSourceLanguage(),
            request.getTargetLanguage()
        );
        
        var result = new TranslationResult();
        result.setResult(translationStr);
        
        return ResponseEntity.ok(result);
    }
} 