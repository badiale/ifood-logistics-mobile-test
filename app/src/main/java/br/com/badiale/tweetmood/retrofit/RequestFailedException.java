package br.com.badiale.tweetmood.retrofit;

import java.io.IOException;

import retrofit2.Response;

public class RequestFailedException extends RuntimeException {
    private Response<?> response;

    RequestFailedException(final Response<?> response) {
        super("Request failed with " + response.code() + "(" + response.message() + "): " + toString(response));
        this.response = response;
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

    public Response<?> getResponse() {
        return response;
    }
}
