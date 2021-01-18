package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DNAFinderServiceImplTest {
    private HumanRepository humanRepository;
    private HumanClassificationMapper humanClassificationMapper;

    @Before
    public void setUp(){
        humanClassificationMapper = Mockito.mock(HumanClassificationMapper.class);
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

}