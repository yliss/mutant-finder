package com.mercadolibre.services.mutantfinder.data.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HumanClassificationStatisticsTemp {
    private long countMutantDNA;
    private long countHumanDNA;
}
