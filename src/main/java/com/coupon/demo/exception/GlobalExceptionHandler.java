package com.coupon.demo.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({LoginFailed.class})
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Login failed");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<Object> handleSQLErrors(DataAccessException ex) {
        SQLException sqlEx = (SQLException) ex.getRootCause();
        switch (sqlEx.getErrorCode()) {
            case 1062:
                return ResponseEntity.status(HttpStatus.CONFLICT).body("You submitted conflicting" +
                        " entries, duplicate entries...");
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        "Internal database error");
        }
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRunTimeExceptions(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ex.getMessage());
    }
}



