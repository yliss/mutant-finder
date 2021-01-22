package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import static org.junit.Assert.*;
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
	public void whenDnaArraysIsValidAndSecuenceMutanNoExistThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCTTCTTT", "GACCGGAG", "GACTTGCA", "GAGCGACT", "CTGTGTGT", "CAATCACA", "TTCGACCT",
				"CTTCTAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistOnceInLastPositionThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"TCAGGTTT","GAGCGGCG","GGCTTGTA","GAGCGACT","CTGTGTCT","CAATCAGA","TTCGACCT","CTTCTAAC"};
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistOnceThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"TCAGGTTT","GAGCGGCG","GGCTTGTA","GAGCGACT","CTGTGTCT","CAATCAGA","TTCGACCT","CTTCTAAC"};
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceInLastPositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"TCACGTTT","GACCTGCG","GACCTGAA","GATCGAGT","CCGTGTCT","CAATCACA","CTCGACAT","CCCCTATC"};
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceXPositionThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCACGTTT", "GACTTTCG", "GACATGAA", "GATTGTGT", "CCGTGTTT", "CAATCACA", "TTCGACAT",
				"CTTCTATC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceIntersectsThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCAGGTTT", "GACGAGCG", "GAGGTGAA", "GGGGGAGT", "CTTTGTCT", "CAATCACA", "TTCGACAT",
				"CTTCTATC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceLastThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCGCGTGT", "GACATTGG", "GACAAGAA", "GATAGCCC", "CCGTATAT", "CAATCTAT", "TTCGACAT",
				"CTTCTTTT" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceInDiagonalLineThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCCCGTAT", "GTCATTCG", "GATACGGA", "GATTGACT", "CCGTTTGT", "CAATCTAA", "TTCGACTT",
				"CTTCTAAT" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistInbigArrayThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
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
				"ACTGACTGACTGACATTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
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
				"CGGACGGACGGACGCGGACGGACGTACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"TGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGACTGACTGACTGTGAC",
				"ACTGACTGACTGACACTGAATGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
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
				"ACTGACTGACTGACATTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTT",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"ACTGACTGACTGACACTGACTGACTGAAACTGACTGACTGAAACTGACTGACTGAACCCC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanNoExistInbigArrayThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
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
				"ACTGACTGACTGACATTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
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
				"ACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"ACTGACTGACTGACATTGACTGACTGACACTGACTGACTGACACTGACTGACTGACACTG",
				"CGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGACGGACGGACGCGGA",
				"ACTGACTGACTGACACTGACTGACTGAAACTGACTGACTGAAACTGACTGACTGAAACTG" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanNoExistTwiceSharedPositionsThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCCCGTAT", "GCCATTCG", "GATACGGA", "GATTCACT", "CCGCGTGT", "CACTCAAA", "TCCGCCCT",
				"CTTCCAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}
	
	
	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanNoExistTwiceVerticalPositionsThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"GCACGTCT","GACCTTAG","GACAAGAA","GATAGACT","CCGTGTTT","CAATCAGA","TTCGACAT","CTTCTAGC"}					;
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertFalse(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceVerticalPositions2ThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"AAAAGC","AAGAGC","ATATGT","AGAAGG","CCCCTA","TCACTG"}	;
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}

	@Test
	public void whenDnaArraysIsValidAndSecuenceMutanExistTwiceVerticalPositionsThenShouldReturnTrue() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = {"GCACGTCT","GACCTTAG","GACAAGAA","GATAGACT","GCGTGTTT","GAATCAGA","GTCGACAT","GTTCTAGC"}	;				
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
	}
	@Test(expected = InvalidDataException.class)
	public void whenDnaArraysIsInValidAndSecuenceMutanExistTwiceVerticalPositionsThenShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "XXXX", "XCGC", "XGCC", "XGCC" };
		dnaFinderService.isMutant(dna);
	}

	@Test
	public void whenDnaArraysIsInValidAndSecuenceMutanExistThridTimesShouldReturnFalse() {
		DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
		final String[] dna = { "TCCCGTAC", "GCCATTGG", "GATACAGA", "GATTCACT", "GCGCGTGT", "CACTCAAA", "TCCGCCCT",
				"CTTCCAAC" };
		final boolean isMutant = dnaFinderService.isMutant(dna);
		assertTrue(isMutant);
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

    @Test (expected = InvalidDataException.class)
    public void whenCallingTheRetrieveStatisticsMethodThenShouldTheClassificationModel(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);
       
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp = null;
        
        when(humanRepository.retrieveStatistics()).thenReturn(humanClassificationStatisticsTemp);
        
        dnaFinderService.retrieveStatistics();
    }
    
    @Test
    public void whenCallingTheRetrieveStatisticsWithNullEntityThenShouldTheClassificationModelNotNull(){
        DNAFinderServiceImpl dnaFinderService = new DNAFinderServiceImpl(humanRepository, humanClassificationMapper);

        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();

        when(humanRepository.retrieveStatistics()).thenReturn(humanClassificationStatisticsTemp);

        final HumanClassificationStatistics humanClassificationStatistics = dnaFinderService.retrieveStatistics();

		assertNotNull(humanClassificationStatistics);
	}
    
    @Test
    public void whenCallingTheRetrieveStatisticsMethodThenShouldTheClassificationModel2(){
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