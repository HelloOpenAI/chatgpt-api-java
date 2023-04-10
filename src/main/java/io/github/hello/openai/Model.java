package io.github.hello.openai;

import io.github.hello.openai.http.HttpClientSupport;
import io.github.hello.openai.http.RetrofitHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

import java.io.IOException;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class Model {

    private final HttpClientSupport httpClient;

    private static final String MODEL_LIST_API = "https://api.openai.com/v1/models";

    public Model(HttpClientSupport httpClient) {
        this.httpClient = httpClient;
    }

    public String list() throws IOException {
        if (httpClient instanceof RetrofitHttpClient) {
            RetrofitHttpClient retrofit = (RetrofitHttpClient) httpClient;
            Response<ResponseBody> response = retrofit.getRetrofit().create(RetrofitApi.class).list().execute();
            return retrofit.readAsString(response);
        } else {
            return httpClient.get(MODEL_LIST_API);
        }
    }

    private interface RetrofitApi {
        @GET("models")
        Call<ResponseBody> list();
    }

}
