package com.mercadolibre.services.mutantfinder.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class HumanTest {
    @Test
    public void Should_Equal_Itself() {
        final Human human = new Human();
        final boolean result = human.equals(human);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final Human human = new Human();
        final boolean result = human.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final Human human = new Human();
        final boolean result = human.equals(EMPTY);

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final Human human = new Human();
        human.setDna(new ArrayList<>());

        final int result1 = human.hashCode();
        final int result2 = human.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final Human human = new Human();
        List<String> dna1 = new ArrayList<>();
        dna1.add("ASDSA");
        human.setDna(dna1);

        List<String> dna2 = new ArrayList<>();
        final Human human2 = new Human(dna2);

        final int result1 = human.hashCode();
        final int result2 = human2.hashCode();

        assertNotEquals(result1, result2);
    }
}