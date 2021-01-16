package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.exceptions.handlers.InvalidDataException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DNAFinderServiceImplTest {
    @Test(expected = InvalidDataException.class)
    public void theDNAArraysIsNullShouldReturnAnError(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl();

        dnaFinderService.isMutant(null);
    }

    @Test
    public void theDNAArraysIsValidAndTheArrayHaveAnVectorThenShouldReturnTrue(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl();

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
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl();

        final String[] dna = {
                "ATGC",
                "CGGT",
                "TTAT",
                "AGAA"};

        final boolean isMutant = dnaFinderService.isMutant(dna);

        assertFalse(isMutant);
    }

}