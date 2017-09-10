package me.sjtumeow.meow.util;

import java.text.Normalizer;
import javax.validation.constraints.AssertTrue;
import org.junit.Test;

import static org.junit.Assert.*;

public class FormatValidatorTest {
    @Test
    public void checkNonNegativeInt() throws Exception {
    }

    @Test
    public void checkPositiveInt() throws Exception {
    }

    @Test
    public void checkPhone() throws Exception {
        assertFalse(FormatValidator.checkPhone("123"));
        assertTrue(FormatValidator.checkPhone("12333333333"));
    }

    @Test
    public void checkPassword() throws Exception {
        assertTrue(FormatValidator.checkPassword("password"));
        assertFalse(FormatValidator.checkPassword("123"));
    }

    @Test
    public void checkSmsCode() throws Exception {
    }

    @Test
    public void checkMainItemType() throws Exception {
    }

    @Test
    public void checkItemType() throws Exception {
    }

}