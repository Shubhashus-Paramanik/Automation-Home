package com.main.ajarul.exception;

public class DeviceAlreadyExistsException extends RuntimeException {
     public DeviceAlreadyExistsException(String message) {
        super(message);
    }
}
