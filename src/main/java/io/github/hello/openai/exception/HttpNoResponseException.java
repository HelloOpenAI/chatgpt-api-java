package io.github.hello.openai.exception;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class HttpNoResponseException extends RuntimeException {

    public HttpNoResponseException(String message) {
        super(message);
    }

}
