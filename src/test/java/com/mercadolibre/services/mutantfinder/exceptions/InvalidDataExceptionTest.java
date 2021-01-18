package com.mercadolibre.services.mutantfinder.exceptions;

import com.mercadolibre.services.mutantfinder.data.entities.HumanEntity;
import com.mercadolibre.services.mutantfinder.models.Human;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class InvalidDataExceptionTest {
    @Test
    public void Should_Equal_Itself() {
        final InvalidDataException invalidDataException = new InvalidDataException("Issue with the code");
        final boolean result = invalidDataException.equals(invalidDataException);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final InvalidDataException invalidDataException = new InvalidDataException("Issue with the code");
        final boolean result = invalidDataException.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final InvalidDataException invalidDataException = new InvalidDataException("Issue with the code");
        final boolean result = invalidDataException.equals(EMPTY);

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final InvalidDataException invalidDataException = new InvalidDataException("Issue with the code");

        final int result1 = invalidDataException.hashCode();
        final int result2 = invalidDataException.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void ShouldGetMethodsRetrieveSameValueThatInitializeTheConstructor() {
        final String errorMessage = "Issue with the code";
        final InvalidDataException invalidDataException = new InvalidDataException(errorMessage);

        assertTrue(invalidDataException.getDescription().equals(errorMessage));
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final InvalidDataException invalidDataException = new InvalidDataException("Issue with the code");

        final InvalidDataException invalidDataException2 = new InvalidDataException("Issue with class");

        final int result1 = invalidDataException.hashCode();
        final int result2 = invalidDataException2.hashCode();

        assertNotEquals(result1, result2);
    }
}