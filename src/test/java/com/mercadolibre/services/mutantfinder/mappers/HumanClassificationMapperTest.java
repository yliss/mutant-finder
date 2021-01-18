package com.mercadolibre.services.mutantfinder.mappers;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class HumanClassificationMapperTest {
    @Test(expected = InvalidDataException.class)
    public void whenEntityIsNullThenShouldReturnException() {
        HumanClassificationMapper humanClassificationMapper = Mockito.mock(HumanClassificationMapper.class);

        Mockito.when(humanClassificationMapper.calculateRatio(Mockito.any())).thenCallRealMethod();

        humanClassificationMapper.calculateRatio(null);
    }

    @Test
    public void whenEntityIsValidThenShouldReturnANumber() {
        HumanClassificationMapper humanClassificationMapper = Mockito.mock(HumanClassificationMapper.class);

        Mockito.when(humanClassificationMapper.calculateRatio(Mockito.any())).thenCallRealMethod();

        HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp(50,200);

        float ratio = humanClassificationMapper.calculateRatio(humanClassificationStatisticsTemp);

        assertTrue(ratio > 0);
    }

    @Test
    public void whenEntityIsValidAndTheMutantCountIsZeroThenShouldReturnZero() {
        HumanClassificationMapper humanClassificationMapper = Mockito.mock(HumanClassificationMapper.class);

        Mockito.when(humanClassificationMapper.calculateRatio(Mockito.any())).thenCallRealMethod();

        HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp(0,200);

        float ratio = humanClassificationMapper.calculateRatio(humanClassificationStatisticsTemp);

        assertTrue(ratio == 0);
    }
}