package com.example.exception;

public class RecipeException extends RuntimeException {

    public RecipeException(final String errorMessage) {
        super(errorMessage);
    }

    public RecipeException(final String errorMessage, final Throwable throwable) {
        super(errorMessage,  throwable);
    }

    public RecipeException(final Throwable ex) {
        super(ex);
    }
}
