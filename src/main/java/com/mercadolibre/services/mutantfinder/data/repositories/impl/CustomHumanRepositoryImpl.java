package com.mercadolibre.services.mutantfinder.data.repositories.impl;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.repositories.CustomHumanRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.math.BigInteger;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomHumanRepositoryImpl implements CustomHumanRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public HumanClassificationStatisticsTemp retrieveStatistics() {
        final String nativeQuery = "SELECT " +
                "(SELECT COUNT(HUM) FROM HUMAN HUM WHERE HUM.IS_MUTANT = FALSE) AS count_mutant_dna," +
                "(SELECT COUNT(HUM) FROM HUMAN HUM WHERE HUM.IS_MUTANT = TRUE) AS count_human_dna";
        Query query = entityManager.createNativeQuery(nativeQuery);
        List<Object[]> list = query.getResultList();
        if(list != null && list.size() > 0) {
            Object[] values = list.get(0);
            final long countMutant = ((BigInteger)values[0]).longValue();
            final long countHuman = ((BigInteger)values[1]).longValue();
            HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                    new HumanClassificationStatisticsTemp();

            humanClassificationStatisticsTemp.setCountMutantDNA(countMutant);
            humanClassificationStatisticsTemp.setCountHumanDNA(countHuman);

            return humanClassificationStatisticsTemp;
        } else {
            return null;
        }
    }
}
