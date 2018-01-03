package br.com.badiale.tweetmood.naturallanguage;

import com.google.common.base.MoreObjects;

class NaturalLanguageResponseDocumentSentiment {
    private Float magnitude;
    private Float score;

    public Float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(final Float magnitude) {
        this.magnitude = magnitude;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(final Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("magnitude", magnitude)
                .add("score", score)
                .toString();
    }
}
