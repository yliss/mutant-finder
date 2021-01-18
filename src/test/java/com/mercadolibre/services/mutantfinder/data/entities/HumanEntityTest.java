package com.mercadolibre.services.mutantfinder.data.entities;

import org.junit.Test;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class HumanEntityTest {
    @Test
    public void Should_Equal_Itself() {
        final HumanEntity humanEntity = new HumanEntity();
        final boolean result = humanEntity.equals(humanEntity);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final HumanEntity humanEntity = new HumanEntity();
        final boolean result = humanEntity.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final HumanEntity humanEntity = new HumanEntity();
        final boolean result = humanEntity.equals(EMPTY);

        assertFalse(result);
    }

    @Test
    public void ShouldGetMethodsRetrieveSameValueThatInitializeTheConstructor() {
        final boolean isMutant = false;
        final String dnaString = "ASDASDASDASDASD";
        final Long hashCode = 12312321L;
        final HumanEntity humanEntity = new
                HumanEntity(hashCode,dnaString,isMutant);

        assertTrue(humanEntity.getDna().equals(dnaString));
        assertTrue(humanEntity.getHashCode() == hashCode);
        assertTrue(humanEntity.getIsMutant() == isMutant);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final HumanEntity humanEntity = new HumanEntity();
        humanEntity.setIsMutant(false);

        final int result1 = humanEntity.hashCode();
        final int result2 = humanEntity.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final HumanEntity humanEntity = new
                HumanEntity(12312321L,"ASDASDASDASDASD",false);

        final HumanEntity humanEntity2 = new HumanEntity();
        humanEntity.setDna("ASDASDASDASDASDAAAA");
        humanEntity.setHashCode(12312321L);

        final int result1 = humanEntity.hashCode();
        final int result2 = humanEntity2.hashCode();

        assertNotEquals(result1, result2);
    }
}