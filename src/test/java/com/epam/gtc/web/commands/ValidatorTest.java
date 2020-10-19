package com.epam.gtc.web.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void isValidEmail() {
        assertTrue(Validator.isValidEmail("test@test.com"));
        assertFalse(Validator.isValidEmail(null));
    }

    @Test
    void isValidNumber() {
        assertTrue(Validator.isValidNumber("1"));
        assertFalse(Validator.isValidNumber(null));
    }

    @Test
    void isValidPassword() {
        assertTrue(Validator.isValidPassword("1111111", "1111111"));
        assertFalse(Validator.isValidPassword(null, null));
        assertFalse(Validator.isValidPassword("111", "111"));
    }

    @Test
    void isValidString() {
        assertTrue(Validator.isValidString("abcdefg"));
        assertFalse(Validator.isValidNumber(null));
    }

    @Test
    void isValidDate() {
        assertTrue(Validator.isValidDate("2020-11-18"));
        assertFalse(Validator.isValidDate(null));
    }

    @Test
    void isValidLength() {
        assertTrue(Validator.isValidLength("123456789", 4));
        assertFalse(Validator.isValidLength(null, 3));
    }


}