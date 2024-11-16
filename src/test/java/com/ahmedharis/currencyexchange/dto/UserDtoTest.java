package com.ahmedharis.currencyexchange.dto;

import com.ahmedharis.currencyexchange.enums.UserType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void validUserDto() {
        UserDto userDto = new UserDto(UserType.ADMIN, 5);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullUserType() {
        UserDto userDto = new UserDto(null, 5);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(1, violations.size());
        assertEquals("User type must not be null.", violations.iterator().next().getMessage());
    }

    @Test
    void negativeTenure() {
        UserDto userDto = new UserDto(UserType.ADMIN, -1);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(1, violations.size());
        assertEquals("Tenure must be positive.", violations.iterator().next().getMessage());
    }

    @Test
    void zeroTenure() {
        UserDto userDto = new UserDto(UserType.ADMIN, 0);
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        assertEquals(1, violations.size());
        assertEquals("Tenure must be positive.", violations.iterator().next().getMessage());
    }
}
