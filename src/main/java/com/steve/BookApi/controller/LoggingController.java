package com.steve.BookApi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.function.Supplier;

public class LoggingController {
    Logger LOG = LoggerFactory.getLogger(LoggingController.class);

    ResponseEntity<Object> timeOperation(Supplier<ResponseEntity<Object>> f) {
        long startTime = System.currentTimeMillis();
        setCorrelationId();

        ResponseEntity<Object> response = f.get();
        LOG.info(String.format("Time to complete operation: time[%d] ms", System.currentTimeMillis() - startTime));
        return response;
    }

    private void setCorrelationId() {
        String correlationId = UUID.randomUUID().toString();
        MDC.put("id", correlationId);
    }
}
