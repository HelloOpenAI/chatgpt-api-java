package io.github.hello.openai.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class SpringRestTemplate implements HttpClientSupport {

    private final RestTemplate rest;

    private final HttpHeaders headers;

    public SpringRestTemplate() {
        System.setProperty("java.net.preferIPv4Stack", Boolean.TRUE.toString());
        rest = new RestTemplate();
        headers = new HttpHeaders();
    }

    @Override
    public void addHeaders(Map<String, String> headers) {
        headers.forEach(this.headers::add);
    }

    @Override
    public void proxy(String host, int port) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);
        rest.setRequestFactory(requestFactory);
    }

    @Override
    public String get(String url) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return rest.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    }

    @Override
    public String post(String url, String jsonPayload) {
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);
        return rest.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

}
