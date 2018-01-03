package br.com.badiale.tweetmood.retrofit;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Response;

public class RequestFailedException extends RuntimeException {
    RequestFailedException(final Request request, final Response<?> response) {
        super("Request to " + request.url() + " failed with " + response.code() + "(" + response.message() + "): " + toString(response));
    }

    private static String toString(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
            return "<empty body>";
        } catch (IOException e) {
            return "<Failed to get body string>";
        }
    }
}
