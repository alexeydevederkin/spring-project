package com.asd.employee.exception;

import java.time.LocalDate;

public class IncorrectHireFireDateException extends RuntimeException {
    public IncorrectHireFireDateException() {
        super("Incorrect hire and fire date of the employee. Hire date must be earlier than fire date.");
    }
}