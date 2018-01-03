package br.com.badiale.tweetmood.naturallanguage;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class NaturalLanguageServiceTest {
    private NaturalLanguageService subject = NaturalLanguageService.getInstance();

    @Test
    public void shouldAnalyseHappySentiment() {
        final Sentiment sentiment = subject.analyse("I'm supper happy!!! :)").blockingFirst();
        assertThat(sentiment, equalTo(Sentiment.HAPPY));
    }

    @Test
    public void shouldAnalyseNeutralSentiment() {
        final Sentiment sentiment = subject.analyse("Just another day").blockingFirst();
        assertThat(sentiment, equalTo(Sentiment.NEUTRAL));
    }

    @Test
    public void shouldAnalyseSadSentiment() {
        final Sentiment sentiment = subject.analyse("Something really bad happened today :(").blockingFirst();
        assertThat(sentiment, equalTo(Sentiment.SAD));
    }
}