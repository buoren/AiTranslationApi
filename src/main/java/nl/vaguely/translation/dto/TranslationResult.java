package nl.vaguely.translation.dto;

public class TranslationResult {
    private String result;

    public TranslationResult(String result) {
        this.result = result;
    }

    public TranslationResult() {
        this.result = "";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
} 