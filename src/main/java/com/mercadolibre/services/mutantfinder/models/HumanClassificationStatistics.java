package com.mercadolibre.services.mutantfinder.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HumanClassificationStatistics {
    private int countMutantDNA;
    private int countHumanDNA;
    private float ratio;
}
