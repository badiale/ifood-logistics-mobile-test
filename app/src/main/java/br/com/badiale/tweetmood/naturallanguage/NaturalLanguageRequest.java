package br.com.badiale.tweetmood.naturallanguage;

import com.google.common.base.MoreObjects;

class NaturalLanguageRequest {
    private final String encodingType = "UTF8";
    private final NaturalLanguageRequestDocument document;

    NaturalLanguageRequest(final String content) {
        this.document = new NaturalLanguageRequestDocument(content);
    }

    public String getEncodingType() {
        return encodingType;
    }

    public NaturalLanguageRequestDocument getDocument() {
        return document;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("encodingType", encodingType)
                .add("document", document)
                .toString();
    }
}
