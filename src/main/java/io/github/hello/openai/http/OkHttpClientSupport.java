package io.github.hello.openai.http;

import io.github.hello.openai.exception.HttpNoResponseException;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class OkHttpClientSupport implements HttpClientSupport {

    private final OkHttpClient.Builder okHttp;

    private final Request.Builder request;

    public OkHttpClientSupport() {
        okHttp = new OkHttpClient.Builder();
        request = new Request.Builder();
    }

    @Override
    public void addHeaders(Map<String, String> headers) {
        headers.forEach(request::addHeader);
    }

    @Override
    public void proxy(String host, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        okHttp.proxy(proxy);
    }

    @Override
    public String get(String url) throws IOException {
        OkHttpClient client = okHttp.build();
        Request realRequest = request.url(url).build();
        try (Response response = client.newCall(realRequest).execute()) {
            ResponseBody body = response.body();
            if (body == null) {
                final String error = String.format("The request [%s] has returned an empty response body", url);
                throw new HttpNoResponseException(error);
            }
            return body.string();
        }
    }

    @Override
    public String post(String url, String jsonPayload) throws IOException {
        OkHttpClient client = okHttp.build();
        MediaType mediaType = MediaType.parse(HttpHeader.APPLICATION_JSON.getValue());
        RequestBody requestBody = RequestBody.create(jsonPayload, mediaType);
        Request realRequest = request.url(url).post(requestBody).build();
        try (Response response = client.newCall(realRequest).execute()) {
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                final String error = String.format("The request [%s] has returned an empty response body", url);
                throw new HttpNoResponseException(error);
            }
            return responseBody.string();
        }
    }

}
