package com.mercadolibre.services.mutantfinder.services;

import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;

public interface DNAFinderService {
    boolean isMutant(String[] dna);
    HumanClassificationStatistics retrieveStatistics();
}
