package com.mercadolibre.services.mutantfinder.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.mercadolibre.services.mutantfinder.utils.StringMessages.DNA_CHAIN_IS_REQUIRED;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Human {
    @NotNull(message = DNA_CHAIN_IS_REQUIRED)
    private List<String> dna;
}
