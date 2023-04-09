package io.github.hello.openai.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class RetrofitHttpClient implements HttpClientSupport {

    private static final String BASE_URL = "https://api.openai.com/v1/";

    private final Retrofit.Builder retrofit;

    private final OkHttpClient.Builder okHttp;

    public RetrofitHttpClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL);
        okHttp = new OkHttpClient.Builder();
    }

    @Override
    public void addHeaders(Map<String, String> headers) {
        okHttp.addInterceptor(chain -> {
            Request.Builder request = chain.request().newBuilder();
            headers.forEach(request::addHeader);
            return chain.proceed(request.build());
        });
        retrofit.client(okHttp.build());
    }

    @Override
    public void proxy(String host, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        okHttp.proxy(proxy);
        retrofit.client(okHttp.build());
    }

    @Override
    public String get(String url) {
        throw new UnsupportedOperationException();
    }

    public Retrofit getRetrofit() {
        return retrofit.build();
    }

}
