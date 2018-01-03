package br.com.badiale.tweetmood.naturallanguage;

import com.google.common.base.MoreObjects;

class NaturalLanguageRequestDocument {
    private final String type = "PLAIN_TEXT";
    private final String content;

    NaturalLanguageRequestDocument(final String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("content", content)
                .toString();
    }
}
