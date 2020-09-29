package com.shilovich.hrbet.dao.exception;

public class PoolOverflowException extends RuntimeException {
    public PoolOverflowException() {
        super();
    }

    public PoolOverflowException(String message) {
        super(message);
    }

    public PoolOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

    public PoolOverflowException(Throwable cause) {
        super(cause);
    }
}