package it.cantest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RestAPIWelcomeTest {

    @Test
    void shouldReturnWhetherisEven() {
        //given
        int number = 8;
        //when
        boolean result = RestAPIWelcome.isEven(number);
        //then
        Assertions.assertTrue(result);
    }


    @Test
    void shouldReturnFalseForNotEven() {
        //given
        int number = 77;
        //when
        boolean result = RestAPIWelcome.isEven(number);
        //then
        Assertions.assertFalse(result);
    }
}