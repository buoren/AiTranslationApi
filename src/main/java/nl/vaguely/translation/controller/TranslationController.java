package nl.vaguely.translation.controller;

import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<List<Translation>> getAllTranslations() {
        return ResponseEntity.ok(translationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Translation> getTranslationById(@PathVariable Long id) {
        return translationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        return ResponseEntity.ok(translationService.save(translation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Translation> updateTranslation(@PathVariable Long id, @RequestBody Translation translation) {
        if (!translationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        translation.setId(id);
        return ResponseEntity.ok(translationService.save(translation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable Long id) {
        if (!translationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        translationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 