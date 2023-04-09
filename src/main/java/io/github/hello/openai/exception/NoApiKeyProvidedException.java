package io.github.hello.openai.exception;

/**
 * @author codeboyzhou <imzhouchen@gmail.com>
 * @since 0.1.0
 */
public class NoApiKeyProvidedException extends RuntimeException {

    public NoApiKeyProvidedException(String message) {
        super(message);
    }

}
