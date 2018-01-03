package br.com.badiale.tweetmood.naturallanguage;

import com.google.common.base.MoreObjects;

class NaturalLanguageResponseDocumentSentence {
    private NaturalLanguageResponseDocumentSentenceText text;
    private NaturalLanguageResponseDocumentSentiment sentiment;

    public NaturalLanguageResponseDocumentSentenceText getText() {
        return text;
    }

    public void setText(final NaturalLanguageResponseDocumentSentenceText text) {
        this.text = text;
    }

    public NaturalLanguageResponseDocumentSentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(final NaturalLanguageResponseDocumentSentiment sentiment) {
        this.sentiment = sentiment;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("sentiment", sentiment)
                .toString();
    }
}
