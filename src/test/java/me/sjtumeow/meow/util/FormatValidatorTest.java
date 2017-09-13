package me.sjtumeow.meow.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FormatValidatorTest {
    @Test
    public void checkNonNegativeInt() throws Exception {
        assertFalse(FormatValidator.checkNonNegativeInt(null));
        assertTrue(FormatValidator.checkNonNegativeInt(100));
        assertTrue(FormatValidator.checkNonNegativeInt(0));
        assertFalse(FormatValidator.checkNonNegativeInt(-33));
    }

    @Test
    public void checkPositiveInt() throws Exception {
        assertFalse(FormatValidator.checkPositiveInt(null));
        assertTrue(FormatValidator.checkPositiveInt(100));
        assertFalse(FormatValidator.checkPositiveInt(0));
        assertFalse(FormatValidator.checkPositiveInt(-33));
    }

    @Test
    public void checkPhone() throws Exception {
        assertFalse(FormatValidator.checkPhone(null));
        assertFalse(FormatValidator.checkPhone("123"));
        assertFalse(FormatValidator.checkPhone("123xxxxyyyy"));
        assertTrue(FormatValidator.checkPhone("12333333333"));
    }

    @Test
    public void checkPassword() throws Exception {
        assertFalse(FormatValidator.checkPassword(null));
        assertTrue(FormatValidator.checkPassword("password"));
        assertTrue(FormatValidator.checkPassword("666666"));
        assertFalse(FormatValidator.checkPassword("123"));
    }

    @Test
    public void checkSmsCode() throws Exception {
        assertFalse(FormatValidator.checkSmsCode(null));
        assertFalse(FormatValidator.checkSmsCode("hacode"));
        assertTrue(FormatValidator.checkSmsCode("666666"));
    }

    @Test
    public void checkMainItemType() throws Exception {
        assertFalse(FormatValidator.checkMainItemType(null));
        assertFalse(FormatValidator.checkMainItemType(-1));
        assertTrue(FormatValidator.checkMainItemType(0));
        assertTrue(FormatValidator.checkMainItemType(1));
        assertTrue(FormatValidator.checkMainItemType(2));
        assertTrue(FormatValidator.checkMainItemType(3));
        assertFalse(FormatValidator.checkMainItemType(4));
    }

    @Test
    public void checkItemType() throws Exception {
        assertFalse(FormatValidator.checkItemType(null));
        assertFalse(FormatValidator.checkItemType(-1));
        assertTrue(FormatValidator.checkItemType(0));
        assertTrue(FormatValidator.checkItemType(1));
        assertTrue(FormatValidator.checkItemType(2));
        assertTrue(FormatValidator.checkItemType(3));
        assertTrue(FormatValidator.checkItemType(4));
        assertFalse(FormatValidator.checkItemType(5));
    }

}