package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.ItemDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;
import com.ahmedharis.currencyexchange.dto.UserDto;
import com.ahmedharis.currencyexchange.enums.ItemCategory;
import com.ahmedharis.currencyexchange.enums.UserType;
import com.ahmedharis.currencyexchange.service.CurrencyService;
import com.ahmedharis.currencyexchange.service.DiscountService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class CalculationServiceImplTest {

    @Mock
    private CurrencyService currencyService;

    @Mock
    private DiscountService discountService;

    @InjectMocks
    private CalculationServiceImpl calculationService;

    private UserDto user = new UserDto(UserType.EMPLOYEE, 1);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculatePayableAmount_withValidBillDto_returnsCorrectPayableAmount() {
        List<ItemDto> items = List.of(
            new ItemDto("Laptop", 1000.0, 1, ItemCategory.ELECTRONICS),
            new ItemDto("Phone", 500.0, 2, ItemCategory.ELECTRONICS)
        );
        BillDto billDto = new BillDto(user, items, "USD", "PKR");

        when(currencyService.getExchangeRate("USD", "PKR")).thenReturn(200.0);
        when(discountService.calculateDiscount(billDto)).thenReturn(100.0);

        PayableAmountDto result = calculationService.calculatePayableAmount(billDto);

        assertEquals(380000, result.payableAmount());
        assertEquals(100.0, result.discountAmountBeforeConversion());
        assertEquals(2000.0, result.totalAmountBeforeConversion());
        assertEquals(200.0, result.conversionRate());
    }

    @Test
    void calculatePayableAmount_withZeroTotalAmount_returnsZeroPayableAmount() {
        List<ItemDto> items = List.of(
            new ItemDto("Laptop", 0.0, 1, ItemCategory.ELECTRONICS)
        );
        BillDto billDto = new BillDto(user, items, "USD", "PKR");

        when(currencyService.getExchangeRate("USD", "PKR")).thenReturn(200.0);
        when(discountService.calculateDiscount(billDto)).thenReturn(0.0);

        PayableAmountDto result = calculationService.calculatePayableAmount(billDto);

        assertEquals(0.0, result.payableAmount());
        assertEquals(0.0, result.discountAmountBeforeConversion());
        assertEquals(0.0, result.totalAmountBeforeConversion());
        assertEquals(200.0, result.conversionRate());
    }
}
