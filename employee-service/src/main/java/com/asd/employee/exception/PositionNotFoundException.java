package com.asd.employee.exception;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(Integer id) {
        super("Position id not found: " + id);
    }
}
