package io.github.hello.openai.http;

import io.github.hello.openai.exception.HttpNoResponseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Map;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class DefaultHttpClient implements HttpClientSupport {

    private static final int OK = 200;

    private Map<String, String> headers;

    private Proxy proxy;

    @Override
    public void addHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public void proxy(String host, int port) {
        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }

    @Override
    public String get(String url) throws IOException {
        // send get request
        HttpURLConnection connection = openConnection(url);
        connection.setRequestMethod("GET");
        connection.connect();

        // read the response
        final int responseCode = connection.getResponseCode();
        if (responseCode == OK) {
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer response = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            connection.disconnect();
            return response.toString();
        } else {
            connection.disconnect();
            final String error = String.format("The request [%s] has returned an empty response body", url);
            throw new HttpNoResponseException(error);
        }
    }

    private HttpURLConnection openConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection urlConnection = proxy == null ? url.openConnection() : url.openConnection(proxy);
        HttpURLConnection connection = (HttpURLConnection) urlConnection;
        // add http headers
        headers.forEach(connection::setRequestProperty);
        return connection;
    }

}
