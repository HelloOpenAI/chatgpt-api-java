package io.github.hello.openai;

import io.github.hello.openai.http.HttpClientSupport;
import io.github.hello.openai.http.RetrofitHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class Model extends ApiRequest {

    private RetrofitApi retrofitApi;

    public Model(HttpClientSupport httpClient) {
        super(httpClient);
        if (retrofit != null) {
            retrofitApi = retrofit.create(RetrofitApi.class);
        }
    }

    public String list() throws IOException {
        if (retrofitApi == null) {
            final String url = baseUrl + "/models";
            return httpClient.get(url);
        } else {
            Response<ResponseBody> response = retrofitApi.list().execute();
            return RetrofitHttpClient.readAsString(response);
        }
    }

    public String retrieve(String modelName) throws IOException {
        if (retrofitApi == null) {
            final String url = baseUrl + "/models/" + modelName;
            return httpClient.get(url);
        } else {
            Response<ResponseBody> response = retrofitApi.retrieve(modelName).execute();
            return RetrofitHttpClient.readAsString(response);
        }
    }

    private interface RetrofitApi {
        @GET("models")
        Call<ResponseBody> list();

        @GET("models/{modelName}")
        Call<ResponseBody> retrieve(@Path("modelName") String modelName);
    }

}
