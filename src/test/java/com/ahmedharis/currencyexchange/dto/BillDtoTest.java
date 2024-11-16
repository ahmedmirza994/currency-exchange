package com.ahmedharis.currencyexchange.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ahmedharis.currencyexchange.enums.ItemCategory;
import com.ahmedharis.currencyexchange.enums.UserType;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BillDtoTest {

  private final Validator validator;

  public BillDtoTest() {
    this.validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  void totalAmountCalculatesCorrectly() {
    UserDto user = new UserDto(UserType.ADMIN, 5);
    List<ItemDto> items =
        List.of(
            new ItemDto("Item1", 10.0, 2, ItemCategory.CLOTHING),
            new ItemDto("Item2", 5.0, 3, ItemCategory.ELECTRONICS));
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    assertEquals(35.0, bill.totalAmount());
  }

  @Test
  void totalAmountWithEmptyItemsList() {
    UserDto user = new UserDto(UserType.ADMIN, 5);
    BillDto bill = new BillDto(user, List.of(), "USD", "EUR");

    assertEquals(0.0, bill.totalAmount());
  }

  @Test
  void validationFailsWhenUserIsNull() {
    List<ItemDto> items = List.of(new ItemDto("Item1", 10.0, 2, ItemCategory.ELECTRONICS));
    BillDto bill = new BillDto(null, items, "USD", "EUR");

    var violations = validator.validate(bill);
    assertEquals(1, violations.size());
  }

  @Test
  void validationFailsWhenItemsAreEmpty() {
    UserDto user = new UserDto(UserType.ADMIN, 5);
    BillDto bill = new BillDto(user, List.of(), "USD", "EUR");

    var violations = validator.validate(bill);
    assertEquals(2, violations.size());
  }

  @Test
  void validationFailsWhenOriginalCurrencyIsInvalid() {
    UserDto user = new UserDto(UserType.ADMIN, 5);
    List<ItemDto> items = List.of(new ItemDto("Item1", 10.0, 2, ItemCategory.CLOTHING));
    BillDto bill = new BillDto(user, items, "US", "EUR");

    var violations = validator.validate(bill);
    assertEquals(1, violations.size());
  }

  @Test
  void validationFailsWhenTargetCurrencyIsInvalid() {
    UserDto user = new UserDto(UserType.ADMIN, 5);
    List<ItemDto> items = List.of(new ItemDto("Item1", 10.0, 2, ItemCategory.CLOTHING));
    BillDto bill = new BillDto(user, items, "USD", "EU");

    var violations = validator.validate(bill);
    assertEquals(1, violations.size());
  }
}
