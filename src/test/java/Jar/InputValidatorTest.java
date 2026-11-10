package Jar;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Jar.InputValidator.InputValidator;

class InputValidatorTest {//test the linkIsValid method

    private final InputValidator validator = new InputValidator();

    @Test
    void validHttpsLinkShouldReturnTrue() {
        assertTrue(validator.linkIsValid("https://example.com"));
    }

    @Test
    void ftpLinkShouldReturnFalse() {
        assertFalse(validator.linkIsValid("ftp://example.com"));
    }

    @Test 
    void noDomainLinkShouldReturnFalse() {
        assertFalse(validator.linkIsValid("https://"));
    }

    @Test
    void validHttpLinkShouldReturnTrue() {
        assertTrue(validator.linkIsValid("http://example.com"));
    }

    @Test 
    void scriptLinkShouldReturnFalse() {
        assertFalse(validator.linkIsValid("javascript:alert(1)"));
    }

    @Test 
    void nullLinkShouldReturnFalse() {
        assertFalse(validator.linkIsValid(null));
    }
}