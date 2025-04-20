package nl.vaguely.translation.repository;

import nl.vaguely.translation.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {
    @Query("SELECT t FROM Translation t WHERE t.sourceText = :sourceText AND t.sourceContext = :sourceContext AND t.sourceLanguage = :sourceLanguage AND t.targetLanguage = :targetLanguage")
    Optional<Translation> findBySourceTextAndSourceLanguageAndTargetLanguage(
        @Param("sourceText") String sourceText,
        @Param("sourceContext") String sourceContext,
        @Param("sourceLanguage") String sourceLanguage,
        @Param("targetLanguage") String targetLanguage
    );
} 