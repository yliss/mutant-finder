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
    public void whenDNAArraysIsNullShouldThenReturnAnError(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
        dnaFinderService.isMutant(null);
    }

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "AAAA", "CCCC", "CCCC", "CCCC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

    @Test(expected = InvalidDataException.class)
	public void whenDnaArraysIsINValidAndSecuenceMutanExistThenShouldReturnAnError() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "AAAA", "CCCC", "CCCC" };
		dnaFinderService.isMutant(dna);
		
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanNotExistThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "CTGA", "TGAC", "GGCT", "ACTG" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInDiagonalSuperiorThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCAGGTTT", "GAGCGGCG", "GGCTTGTA", "GAGCGACT", "CTGTGTCT", "CAATCAGA", "TTCGACCT",
							   "CTTCTAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInDiagonalInferiorThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCAGGTTT", "GAGCGGCG", "GACTTGTA", "GAGCGACT", "CTGTGTCT", "CAATCATA", "TTCGATCT",
			                   "CTTCTAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInDiagonalInvertedLastThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"TCGCGTGT","GACATTGG","GACAAGAA","GATAGCCT","CCGTATAT","CAATCAAA","TTCGACAT","CTTCTAGA"}	;
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInDiagonalInvertedThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCCCGTAT", "GTCATTCG", "GATACGGA", "GATTGACT", "CCGTGTGT", "CAATCAAA", "TTCGACCT",
				"CTTCTAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInDiagonalInvertedMiddleThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "GCACGTCT", "AACCTTAG", "GACAAGAA", "GATAGACT", "CCGTGTAT", "CAATCAGA", "TTCGACAT",
				"CTTCTAGC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInVerticalFirstPositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "GCACGTCT", "GACCTTAG", "GACAAGAA", "GATAGACT", "CCGTGTTT", "CAATCAGA", "TTCGACAT",
				"CTTCTAGC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInVerticalMiddlePositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "GCACGTCT", "AACCTTCG", "GACAAGGA", "GATAGCAT", "CAGTGTAT", "CAATCAGA", "TTCGACCT",
				"CTTCTACC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
					
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInVerticalLastPositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "GCACGTCT", "AACCTTCG", "GTCAGGCA", "GTTCCAAA", "CAGTGTGT", "CAATCAAT", "TTCGACCT",
				"CTCGCGCT" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInVerticalLastUPPositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "GCACGTAT", "AACCTTTT", "GTCAGGCT", "GTTACAGT", "CAGTGTAT", "CAATCACC", "TTCGACTT",
				"CTCGCGCC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
    @Test
    public void whenParameterIsValidAndSecuenceMutanExistInLastVerticalLastPositionThenRetunTrue(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
        final String[] dna = 
        	   { "ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACAHTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"ACTGACTGACTGACACTGACTGACTGAAACTGACTGACTGAAACTGACTGACTGAAACTG",
				"ACTGACTGACTGACACTGACTGACTGCCACTGCCTGACTGCCACTGACTGCCTGCCACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTA",
				"ACTGACTGACTGACAHTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTT",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"ACTGACTGACTGACACTGACTGACTGAAACTGACTGACTGAAACTGACTGACTGAACCCC" };

        final boolean isMutant = dnaFinderService.isMutant(dna);

        assertTrue(isMutant);
    }
}