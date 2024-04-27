package com.backend.btest.exception;

public class DeviceNotAvailableException extends RuntimeException {
    public DeviceNotAvailableException(String message) {
        super(message);
    }
}
