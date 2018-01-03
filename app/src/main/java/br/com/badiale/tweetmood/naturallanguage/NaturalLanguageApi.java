package br.com.badiale.tweetmood.naturallanguage;

import br.com.badiale.tweetmood.BuildConfig;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface NaturalLanguageApi {
    @POST("/v1/documents:analyzeSentiment?key=" + BuildConfig.GOOGLE_CLOUD_API_KEY)
    Call<NaturalLanguageResponse> analyse(@Body NaturalLanguageRequest request);
}
