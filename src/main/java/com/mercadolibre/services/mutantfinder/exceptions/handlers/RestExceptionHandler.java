package com.mercadolibre.services.mutantfinder.exceptions.handlers;

import com.mercadolibre.services.mutantfinder.models.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = {InvalidDataException.class})
    protected ResponseEntity<Object> handleInvalidData(
            InvalidDataException ex, WebRequest request) {
        final Message message = new Message(ex.getDescription());
        return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}