package nl.vaguely.translation.service;

import lombok.RequiredArgsConstructor;
import nl.vaguely.translation.model.Translation;
import nl.vaguely.translation.repository.TranslationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationRepository translationRepository;

    public List<Translation> findAll() {
        return translationRepository.findAll();
    }

    public Optional<Translation> findById(Long id) {
        return translationRepository.findById(id);
    }

    @Transactional
    public Translation save(Translation translation) {
        return translationRepository.save(translation);
    }

    @Transactional
    public void deleteById(Long id) {
        translationRepository.deleteById(id);
    }
} 