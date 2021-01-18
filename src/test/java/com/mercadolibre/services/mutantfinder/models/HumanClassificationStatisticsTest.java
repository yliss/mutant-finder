package com.mercadolibre.services.mutantfinder.models;

import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class HumanClassificationStatisticsTest {
    @Test
    public void Should_Equal_Itself() {
        final HumanClassificationStatistics humanClassificationStatistics = new HumanClassificationStatistics();
        final boolean result = humanClassificationStatistics.equals(humanClassificationStatistics);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final HumanClassificationStatistics humanClassificationStatistics = new HumanClassificationStatistics();
        final boolean result = humanClassificationStatistics.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final HumanClassificationStatistics humanClassificationStatistics = new HumanClassificationStatistics();
        final boolean result = humanClassificationStatistics.equals(new String(EMPTY));

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final HumanClassificationStatistics humanClassificationStatistics =
                new HumanClassificationStatistics(1,1,0.2f);

        final int result1 = humanClassificationStatistics.hashCode();
        final int result2 = humanClassificationStatistics.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final HumanClassificationStatistics humanClassificationStatistics = new HumanClassificationStatistics();
        humanClassificationStatistics.setCountHumanDNA(1);
        humanClassificationStatistics.setCountMutantDNA(2);
        humanClassificationStatistics.setRatio(1.0F);

        final HumanClassificationStatistics humanClassificationStatistics2 = new HumanClassificationStatistics();
        humanClassificationStatistics2.setCountHumanDNA(1);

        final int result1 = humanClassificationStatistics.hashCode();
        final int result2 = humanClassificationStatistics2.hashCode();

        assertNotEquals(result1, result2);
    }
}