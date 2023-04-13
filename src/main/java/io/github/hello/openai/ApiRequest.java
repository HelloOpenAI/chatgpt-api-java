package io.github.hello.openai;

import io.github.hello.openai.http.HttpClientSupport;
import io.github.hello.openai.http.RetrofitHttpClient;
import retrofit2.Retrofit;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class ApiRequest {

    protected final String baseUrl = "https://api.openai.com/v1";

    protected final HttpClientSupport httpClient;

    protected Retrofit retrofit;

    public ApiRequest(HttpClientSupport httpClient) {
        this.httpClient = httpClient;
        if (httpClient instanceof RetrofitHttpClient) {
            retrofit = ((RetrofitHttpClient) httpClient).getRetrofit();
        }
    }

}
