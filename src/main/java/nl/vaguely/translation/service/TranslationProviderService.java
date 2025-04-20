package nl.vaguely.translation.service;

import nl.vaguely.translation.model.Translation;

public interface TranslationProviderService {
    Translation generateTranslation(Translation translation);
} 