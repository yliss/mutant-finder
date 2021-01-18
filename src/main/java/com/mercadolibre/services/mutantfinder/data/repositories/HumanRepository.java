package com.mercadolibre.services.mutantfinder.data.repositories;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.entities.HumanEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends CrudRepository<HumanEntity,Long>,CustomHumanRepository {
    /*
    @Query(value = "select " +
            "new com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp(" +
            "count(human),0" +
            ") from HumanEntity human")
    HumanClassificationStatisticsTemp retrieveStatistics();
    */
}