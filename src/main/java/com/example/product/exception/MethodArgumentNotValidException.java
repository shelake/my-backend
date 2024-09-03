package com.example.product.exception;
import java.util.List;

public class MethodArgumentNotValidException extends Exception {
    private List<String> errors;

    public MethodArgumentNotValidException(List<String> errors) {
        super(String.join(", ", errors)); // Converts list to single string
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
        }
}


