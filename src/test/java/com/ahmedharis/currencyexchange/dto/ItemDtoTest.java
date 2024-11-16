package com.ahmedharis.currencyexchange.dto;

import com.ahmedharis.currencyexchange.enums.ItemCategory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ItemDtoTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validItemDto() {
        ItemDto itemDto = new ItemDto("Item1", 10.0, 2, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void nullName() {
        ItemDto itemDto = new ItemDto(null, 10.0, 2, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Name must not be null.", violations.iterator().next().getMessage());
    }

    @Test
    void nullPrice() {
        ItemDto itemDto = new ItemDto("Item1", null, 2, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Price must not be null.", violations.iterator().next().getMessage());
    }

    @Test
    void negativePrice() {
        ItemDto itemDto = new ItemDto("Item1", -10.0, 2, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Price must be positive.", violations.iterator().next().getMessage());
    }

    @Test
    void nullQuantity() {
        ItemDto itemDto = new ItemDto("Item1", 10.0, null, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Quantity must not be null.", violations.iterator().next().getMessage());
    }

    @Test
    void negativeQuantity() {
        ItemDto itemDto = new ItemDto("Item1", 10.0, -2, ItemCategory.ELECTRONICS);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Quantity must be positive.", violations.iterator().next().getMessage());
    }

    @Test
    void nullCategory() {
        ItemDto itemDto = new ItemDto("Item1", 10.0, 2, null);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertEquals(1, violations.size());
        assertEquals("Category must not be null.", violations.iterator().next().getMessage());
    }
}
