package com.mercadolibre.services.mutantfinder.data.repositories;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;

public interface CustomHumanRepository {
    HumanClassificationStatisticsTemp retrieveStatistics();
}