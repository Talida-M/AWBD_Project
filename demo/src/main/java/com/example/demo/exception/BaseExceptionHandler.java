package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseExceptionHandler {
    protected ResponseEntity<Object> buildResponse(BaseException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", ex.getStatus().toString());
        body.put("error", ex.getStatus().toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return ResponseEntity.status(ex.getStatus()).body(body);
    }
}
