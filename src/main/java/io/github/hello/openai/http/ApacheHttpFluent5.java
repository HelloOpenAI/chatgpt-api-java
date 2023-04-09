package io.github.hello.openai.http;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.core5.http.HttpHost;

import java.io.IOException;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class ApacheHttpFluent5 implements HttpClientSupport {

    private Map<String, String> headers;

    private HttpHost proxy;

    @Override
    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void proxy(String host, int port) {
        proxy = new HttpHost(host, port);
    }

    @Override
    public String get(String url) throws IOException {
        Request request = Request.get(url);
        headers.forEach(request::addHeader);
        request.viaProxy(proxy);
        Response response = request.execute();
        return response.returnContent().asString();
    }

}
