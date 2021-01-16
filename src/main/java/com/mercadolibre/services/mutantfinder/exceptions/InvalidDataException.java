package com.mercadolibre.services.mutantfinder.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidDataException extends RuntimeException {
    private final String description;
}