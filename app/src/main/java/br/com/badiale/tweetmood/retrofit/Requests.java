package br.com.badiale.tweetmood.retrofit;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requests {
    public static <T> Observable<T> enqueue(final Call<T> call) {
        return Observable.create(emmiter -> call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(final Call<T> call, final Response<T> response) {
                if (response.isSuccessful()) {
                    emmiter.onNext(response.body());
                    emmiter.onComplete();
                    return;
                }
                emmiter.onError(new RequestFailedException(response));
            }

            @Override
            public void onFailure(final Call<T> call, final Throwable throwable) {
                emmiter.onError(throwable);
            }
        }));
    }
}