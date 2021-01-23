package com.mercadolibre.services.mutantfinder.data.repositories.impl;

import static org.junit.Assert.assertFalse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EntityManager.class, EntityManagerFactory.class })
public class CustomHumanRepositoryImplTest {

	@MockBean
	private EntityManager entityManager;

	Query query;

	@Before
	public void setUp() {
		entityManager = Mockito.mock(EntityManager.class);
		query = Mockito.mock(Query.class);
		String nativeQuery = "SELECT "
				+ "(SELECT COUNT(HUM) FROM HUMAN HUM WHERE HUM.IS_MUTANT = FALSE) AS count_mutant_dna,"
				+ "(SELECT COUNT(HUM) FROM HUMAN HUM WHERE HUM.IS_MUTANT = TRUE) AS count_human_dna";
		Mockito.when(entityManager.createNativeQuery(nativeQuery)).thenReturn(query);

	}

	@Test
	public void whentHumanAndMutantHaveBeenCountedThenReturnAValidCustomHumanResult() {
		final int numberHuman = 5;
		final int numberMutant = 5;
		
		
		List<Object[]> listResult = setCount(numberHuman, numberMutant);
		
		Mockito.when(query.getResultList()).thenReturn(listResult);
		
		CustomHumanRepositoryImpl customHumanRepository = new CustomHumanRepositoryImpl(entityManager);
		
		HumanClassificationStatisticsTemp stats = customHumanRepository.retrieveStatistics();
		
		assertNotNull(stats);
		assertEquals(stats.getCountHumanDNA(), numberHuman);
		assertEquals(stats.getCountMutantDNA(), numberMutant);
	}

	@Test
	public void whentHumanStatasIsZeroThenReturnAValidCustomHumanResult() {
		final int numberHuman = 0;
		final int numberMutant = 5;
		
		
		List<Object[]> listResult = setCount(numberHuman, numberMutant);
		
		Mockito.when(query.getResultList()).thenReturn(listResult);
		
		CustomHumanRepositoryImpl customHumanRepository = new CustomHumanRepositoryImpl(entityManager);
		
		HumanClassificationStatisticsTemp stats = customHumanRepository.retrieveStatistics();
		
		assertNotNull(stats);
		assertEquals(stats.getCountHumanDNA(), numberHuman);
		assertEquals(stats.getCountMutantDNA(), numberMutant);
	}
	
	@Test
	public void whentMutantStatsIsZeroThenReturnAValidCustomHumanResult() {
		final int numberHuman = 10;
		final int numberMutant = 0;
		
		
		List<Object[]> listResult = setCount(numberHuman, numberMutant);
		
		Mockito.when(query.getResultList()).thenReturn(listResult);
		
		CustomHumanRepositoryImpl customHumanRepository = new CustomHumanRepositoryImpl(entityManager);
		
		HumanClassificationStatisticsTemp stats = customHumanRepository.retrieveStatistics();
		
		assertNotNull(stats);
		assertEquals(stats.getCountHumanDNA(), numberHuman);
		assertEquals(stats.getCountMutantDNA(), numberMutant);
	}
	
	@Test
	public void whentMutantStatsIsEmptyThenReturnAValidCustomHumanResult() {
		List<Object[]> listResult = new ArrayList <Object[]>();
		
		Mockito.when(query.getResultList()).thenReturn(listResult);
		
		CustomHumanRepositoryImpl customHumanRepository = new CustomHumanRepositoryImpl(entityManager);
		
		HumanClassificationStatisticsTemp stats = customHumanRepository.retrieveStatistics();
		
		assertNull(stats);
	}
	
	@Test
	public void whentMutantStatsNoExistThenReturnAValidCustomHumanResult() {
		
		List<Object[]> listResult = null;
		
		Mockito.when(query.getResultList()).thenReturn(listResult);
		
		CustomHumanRepositoryImpl customHumanRepository = new CustomHumanRepositoryImpl(entityManager);
		
		HumanClassificationStatisticsTemp stats = customHumanRepository.retrieveStatistics();
		
		assertNull(stats);
	}
	
	private List<Object[]> setCount(int numberHuman, int numberMutant) {
		List<Object[]> listResult = new ArrayList<Object[]>();
		Object[] obj = new Object[2];
		BigInteger humanB = BigInteger.valueOf(numberHuman);
		BigInteger mutantB = BigInteger.valueOf(numberMutant);
		obj[0] = humanB;
		obj[1] = mutantB;

		listResult.add(obj);
		return listResult;
	}

}
