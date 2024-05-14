package br.com.odontoflow.controller.exception;

public class ControllerNotFoundException extends  RuntimeException {
    public ControllerNotFoundException(String message) {
        super(message);
    }
}