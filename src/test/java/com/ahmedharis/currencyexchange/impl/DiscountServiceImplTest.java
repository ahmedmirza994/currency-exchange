package com.ahmedharis.currencyexchange.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.ItemDto;
import com.ahmedharis.currencyexchange.dto.UserDto;
import com.ahmedharis.currencyexchange.enums.ItemCategory;
import com.ahmedharis.currencyexchange.enums.UserType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class DiscountServiceImplTest {

  @InjectMocks private DiscountServiceImpl discountService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private ItemDto item = new ItemDto("Item1", 200.0, 1, ItemCategory.ELECTRONICS);

  @Test
  void calculateDiscount_employeeUser() {
    UserDto user = new UserDto(UserType.EMPLOYEE, 1);
    List<ItemDto> items = List.of(item);
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    Double discount = discountService.calculateDiscount(bill);

    assertEquals(70.0, discount);
  }

  @Test
  void calculateDiscount_affiliateUser() {
    UserDto user = new UserDto(UserType.AFFILIATE, 1);
    List<ItemDto> items = List.of(item);
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    Double discount = discountService.calculateDiscount(bill);

    assertEquals(30.0, discount);
  }

  @Test
  void calculateDiscount_longTermUser() {
    UserDto user = new UserDto(UserType.CUSTOMER, 3);
    List<ItemDto> items = List.of(item);
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    Double discount = discountService.calculateDiscount(bill);

    assertEquals(20, discount);
  }

  @Test
  void calculateDiscount_noDiscountUser() {
    UserDto user = new UserDto(UserType.CUSTOMER, 1);
    List<ItemDto> items = List.of(item);
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    Double discount = discountService.calculateDiscount(bill);

    assertEquals(10.0, discount);
  }

  @Test
  void calculateDiscount_groceryItemsOnly() {
    UserDto user = new UserDto(UserType.EMPLOYEE, 1);
    List<ItemDto> items = List.of(new ItemDto("Item1", 200.0, 1, ItemCategory.GROCERY));
    BillDto bill = new BillDto(user, items, "USD", "EUR");

    Double discount = discountService.calculateDiscount(bill);

    assertEquals(10.0, discount);
  }
}
