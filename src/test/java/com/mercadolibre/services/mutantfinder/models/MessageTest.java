package com.mercadolibre.services.mutantfinder.models;

import org.junit.Test;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;

public class MessageTest {
    @Test
    public void Should_Equal_Itself() {
        final Message message = new Message();
        final boolean result = message.equals(message);

        assertTrue(result);
    }

    @Test
    public void Should_Not_Equal_Null() {
        final Message message = new Message();
        final boolean result = message.equals(null);

        assertFalse(result);
    }


    @Test
    public void Should_Not_Equal_Object_Of_Different_Type() {
        final Message message = new Message();
        final boolean result = message.equals(new String(EMPTY));

        assertFalse(result);
    }

    @Test
    public void Should_Generate_Same_Hash_Code_Every_Time() {
        final Message message = new Message("The application works very well");

        final int result1 = message.hashCode();
        final int result2 = message.hashCode();

        assertEquals(result1, result2);
    }

    @Test
    public void Should_Generate_Different_Hash_Code_For_Different_Objects() {
        final Message message = new Message();
        message.setCode("12312312");
        message.setDescription("This is the first message");

        final Message message2 = new Message("10111","This is the first message");

        final int result1 = message.hashCode();
        final int result2 = message2.hashCode();

        assertNotEquals(result1, result2);
    }
}