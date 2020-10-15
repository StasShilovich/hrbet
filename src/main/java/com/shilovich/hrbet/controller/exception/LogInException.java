package com.shilovich.hrbet.controller.exception;

public class LogInException extends CommandException {
    public LogInException() {
        super();
    }

    public LogInException(String message) {
        super(message);
    }

    public LogInException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogInException(Throwable cause) {
        super(cause);
    }
}
