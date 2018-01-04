package br.com.badiale.tweetmood.naturallanguage;

import br.com.badiale.tweetmood.retrofit.Requests;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NaturalLanguageService {
    private static final NaturalLanguageService INSTANCE = new NaturalLanguageService();

    private final NaturalLanguageApi api;

    public static NaturalLanguageService getInstance() {
        return INSTANCE;
    }

    private NaturalLanguageService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://language.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(NaturalLanguageApi.class);
    }

    public Observable<Sentiment> analyse(String text) {
        return Requests.enqueue(api.analyse(new NaturalLanguageRequest(text)))
                .map(response -> {
                    if (response.getDocumentSentiment().getScore() > 0.3) {
                        return Sentiment.HAPPY;
                    }
                    if (response.getDocumentSentiment().getScore() < -0.3) {
                        return Sentiment.SAD;
                    }
                    return Sentiment.NEUTRAL;
                });
    }
}
