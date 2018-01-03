package br.com.badiale.tweetmood.twitter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface TwitterApi {
    @POST("/oauth2/token")
    @FormUrlEncoded
    Call<TwitterAuthenticationResult> oauthToken(
            @Header("Authorization") String basicAuthentication,
            @Field("grant_type") String grantType);

    @GET("/1.1/search/tweets.json?result_type=recent&since_id=1")
    Call<TwitterSearchResult> searchTweets(
            @Header("Authorization") String bearerToken,
            @Query("q") String query);
}
