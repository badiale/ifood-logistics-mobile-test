package br.com.badiale.tweetmood.naturallanguage;

import com.google.common.base.MoreObjects;

import java.util.List;

class NaturalLanguageResponse {
    private NaturalLanguageResponseDocumentSentiment documentSentiment;
    private String language;
    private List<NaturalLanguageResponseDocumentSentence> sentences;

    public NaturalLanguageResponseDocumentSentiment getDocumentSentiment() {
        return documentSentiment;
    }

    public void setDocumentSentiment(final NaturalLanguageResponseDocumentSentiment documentSentiment) {
        this.documentSentiment = documentSentiment;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public List<NaturalLanguageResponseDocumentSentence> getSentences() {
        return sentences;
    }

    public void setSentences(final List<NaturalLanguageResponseDocumentSentence> sentences) {
        this.sentences = sentences;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("documentSentiment", documentSentiment)
                .add("language", language)
                .add("sentences", sentences)
                .toString();
    }
}
