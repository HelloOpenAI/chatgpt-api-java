package io.github.hello.openai;

import io.github.hello.openai.http.HttpClientSupport;
import io.github.hello.openai.http.RetrofitHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.io.IOException;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class TextCompletion extends ApiRequest {

    private RetrofitApi retrofitApi;

    public TextCompletion(HttpClientSupport httpClient) {
        super(httpClient);
        if (retrofit != null) {
            retrofitApi = retrofit.create(RetrofitApi.class);
        }
    }

    public String create(String jsonPayload) throws IOException {
        if (retrofitApi == null) {
            final String url = baseUrl + "/completions";
            return httpClient.post(url, jsonPayload);
        } else {
            Response<ResponseBody> response = retrofitApi.create(jsonPayload).execute();
            return RetrofitHttpClient.readAsString(response);
        }
    }

    private interface RetrofitApi {
        @POST("completions")
        Call<ResponseBody> create(@Body String jsonPayload);
    }

}
