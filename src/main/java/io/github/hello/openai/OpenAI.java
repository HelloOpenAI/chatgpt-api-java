package io.github.hello.openai;

import io.github.hello.openai.exception.NoApiKeyProvidedException;
import io.github.hello.openai.http.HttpClientSupport;
import io.github.hello.openai.http.HttpHeader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class OpenAI {

    private final String apiKey;

    private HttpClientSupport httpClient;

    private OpenAI(String apiKey) {
        this.apiKey = apiKey;
    }

    public static OpenAI init(String apiKey) {
        return new OpenAI(apiKey);
    }

    public static OpenAI init() {
        final String apiKey = System.getenv("OPENAI_API_KEY");
        return init(apiKey);
    }

    public OpenAI use(HttpClientSupport httpClient) {
        Map<String, String> headers = new HashMap<>(2);
        headers.put(HttpHeader.CONTENT_TYPE.getValue(), HttpHeader.APPLICATION_JSON.getValue());
        headers.put(HttpHeader.AUTHORIZATION.getValue(), String.format("Bearer %s", apiKey));
        httpClient.addHeaders(headers);
        this.httpClient = httpClient;
        return this;
    }

    public OpenAI proxy(String host, int port) {
        httpClient.proxy(host, port);
        return this;
    }

    public OpenAI ready() {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            final String error = "No API key provided. You can set your API key in code using 'OpenAI.init(<API-KEY>)'," +
                    " or you can set the environment variable 'OPENAI_API_KEY=<API-KEY>'. If your API key is stored in a file," +
                    " you can use 'OpenAI.init(<API-KEY-FILE-PATH>)'. You can generate API keys in the OpenAI web interface." +
                    " See https://onboard.openai.com for details, or email support@openai.com if you have any questions.";
            throw new NoApiKeyProvidedException(error);
        }
        return this;
    }

    public Model model() {
        return new Model(httpClient);
    }

}
