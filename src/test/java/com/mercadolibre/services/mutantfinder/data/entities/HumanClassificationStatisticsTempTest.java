package com.mercadolibre.services.mutantfinder.data.entities;

import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class HumanClassificationStatisticsTempTest {
    @Test
    public void Should_Equal_Itself() {
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();
        final boolean result = humanClassificationStatisticsTemp.equals(humanClassificationStatisticsTemp);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();
        final boolean result = humanClassificationStatisticsTemp.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();
        final boolean result = humanClassificationStatisticsTemp.equals(EMPTY);

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();
        humanClassificationStatisticsTemp.setCountHumanDNA(123L);

        final int result1 = humanClassificationStatisticsTemp.hashCode();
        final int result2 = humanClassificationStatisticsTemp.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                new HumanClassificationStatisticsTemp();
        humanClassificationStatisticsTemp.setCountHumanDNA(4432L);

        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp2 =
                new HumanClassificationStatisticsTemp();
        humanClassificationStatisticsTemp2.setCountMutantDNA(132131L);

        final int result1 = humanClassificationStatisticsTemp.hashCode();
        final int result2 = humanClassificationStatisticsTemp2.hashCode();

        assertNotEquals(result1, result2);
    }
}