package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.entities.HumanEntity;
import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import com.mercadolibre.services.mutantfinder.services.DNAFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAFinderServiceImpl implements DNAFinderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DNAFinderServiceImpl.class);
    private final HumanRepository humanRepository;
    private final HumanClassificationMapper humanClassificationMapper;

    @Autowired
    public DNAFinderServiceImpl(HumanRepository humanRepository, HumanClassificationMapper humanClassificationMapper) {
        this.humanRepository = humanRepository;
        this.humanClassificationMapper = humanClassificationMapper;
    }

    @Override
    public boolean isMutant(String[] dnaArray) {
        LOGGER.info("Starting isMutant method inside service");
        if(dnaArray == null || dnaArray.length <= 1) {
            LOGGER.error("The DNA parameter is not valid");
            throw new InvalidDataException("The DNA parameter is not valid");
        }

        /*
          TODO: search how many vector there are inside the dna array
          remove the vector that not have 4 equals chars
        */

        HumanEntity humanEntity = new HumanEntity();
        humanEntity.setHashCode(simpleHash(dnaArray));
        humanEntity.setDna(String.join("",dnaArray));
        humanEntity.setIsMutant(false);

        humanRepository.save(humanEntity);

        LOGGER.info("Finishing isMutant method inside service");
        return false;
    }

    @Override
    public HumanClassificationStatistics retrieveStatistics() {
        LOGGER.info("Starting retrieveStatistics method");
        LOGGER.info("Calling the db to retrieve the statistics");
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                humanRepository.retrieveStatistics();

        LOGGER.info("Mapping the entity to the model");
        final HumanClassificationStatistics humanClassificationStatistics = humanClassificationMapper
                .mapEntityToModel(humanClassificationStatisticsTemp);

        LOGGER.info("Finishing the retrieveStatistics method");
        return humanClassificationStatistics;
    }

    private long simpleHash(final String[] dnaArray) {
        int hash = 7;
        for (int i = 0; i < dnaArray.length; i++) {
            String row = dnaArray[i];
            for (int j = 0; j < row.length(); j++) {
                hash = hash*31 + row.charAt(j);
            }
        }

        return hash;
    }
}
