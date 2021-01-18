package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DNAFinderServiceImplTest {
    private HumanRepository humanRepository;
    private HumanClassificationMapper humanClassificationMapper;

    @Before
    public void setUp(){
        humanClassificationMapper = Mappers.getMapper(HumanClassificationMapper.class);

        humanRepository = Mockito.mock(HumanRepository.class);
    }

    @Test(expected = InvalidDataException.class)
    public void theDNAArraysIsNullShouldReturnAnError(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        dnaFinderService.isMutant(null);
    }

    @Ignore
    @Test
    public void theDNAArraysIsValidAndTheArrayHaveAnVectorThenShouldReturnTrue(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        final String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"};

        final boolean isMutant = dnaFinderService.isMutant(dna);

        assertTrue(isMutant);
    }

    @Test
    public void theDNAArraysIsValidAndTheArrayDoesNotHaveAnVectorThenShouldReturnFalse(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        final String[] dna = {
                "ATGC",
                "CGGT",
                "TTAT",
                "AGAA"};

        final boolean isMutant = dnaFinderService.isMutant(dna);

        assertFalse(isMutant);
    }

    @Test
    public void whenCallingTheRetrieveStatisticsMethodThenShouldTheClassificationModelNotNull(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();

        when(humanRepository.retrieveStatistics()).thenReturn(humanClassificationStatisticsTemp);

        final HumanClassificationStatistics humanClassificationStatistics = dnaFinderService.retrieveStatistics();

        assertNotNull(humanClassificationStatistics);
    }

    @Test
    public void whenCallingTheRetrieveStatisticsMethodThenShouldTheClassificationModel(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();

        humanClassificationStatisticsTemp.setCountHumanDNA(123);
        humanClassificationStatisticsTemp.setCountMutantDNA(500);

        when(humanRepository.retrieveStatistics()).thenReturn(humanClassificationStatisticsTemp);

        final HumanClassificationStatistics humanClassificationStatistics = dnaFinderService.retrieveStatistics();

        assertNotNull(humanClassificationStatistics);
        assertTrue(humanClassificationStatisticsTemp.getCountHumanDNA() ==
                        humanClassificationStatistics.getCountHumanDNA());
        assertTrue(humanClassificationStatisticsTemp.getCountMutantDNA() ==
                humanClassificationStatistics.getCountMutantDNA());
    }

}