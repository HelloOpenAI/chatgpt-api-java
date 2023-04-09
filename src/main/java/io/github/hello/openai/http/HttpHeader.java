package io.github.hello.openai.http;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public enum HttpHeader {
    /**
     * Header names
     */
    CONTENT_TYPE("Content-Type"),
    AUTHORIZATION("Authorization"),

    /**
     * Header values
     */
    APPLICATION_JSON("application/json"),

    ;

    private final String value;

    HttpHeader(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
