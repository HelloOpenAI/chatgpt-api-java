package io.github.hello.openai.http;

import java.io.IOException;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public interface HttpClientSupport {

    void addHeaders(Map<String, String> headers);

    void proxy(String host, int port);

    String get(String url) throws IOException;

    String post(String url, String jsonPayload) throws IOException;
}
