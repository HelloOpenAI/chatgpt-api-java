package io.github.hello.openai.http;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class ApacheHttpClient5 implements HttpClientSupport {

    private final HttpClientBuilder httpClientBuilder;

    private Map<String, String> headers;

    public ApacheHttpClient5() {
        httpClientBuilder = HttpClientBuilder.create();
    }

    @Override
    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void proxy(String host, int port) {
        HttpHost proxy = new HttpHost(host, port);
        httpClientBuilder.setProxy(proxy);
    }

    @Override
    public String get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        headers.forEach(httpGet::addHeader);
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            return httpClient.execute(httpGet, response -> EntityUtils.toString(response.getEntity()));
        }
    }

    @Override
    public String post(String url, String jsonPayload) throws IOException {
        ClassicHttpRequest request = ClassicRequestBuilder.post().setUri(url).setEntity(jsonPayload).build();
        headers.forEach(request::addHeader);
        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            return httpClient.execute(request, response -> EntityUtils.toString(response.getEntity()));
        }
    }

}
