package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;
import com.ahmedharis.currencyexchange.dto.UserDto;
import com.ahmedharis.currencyexchange.enums.UserType;
import com.ahmedharis.currencyexchange.service.CalculationService;
import com.ahmedharis.currencyexchange.service.CurrencyService;
import com.ahmedharis.currencyexchange.service.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class CalculationServiceImpl implements CalculationService {
    private final CurrencyService currencyService;
    private final DiscountService discountService;

    public CalculationServiceImpl(CurrencyService currencyService, DiscountService discountService) {
        this.currencyService = currencyService;
        this.discountService = discountService;
    }

    @Override
    public PayableAmountDto calculatePayableAmount(BillDto billDto) {
        Double exchangeRate = currencyService.getExchangeRate(billDto.originalCurrency(), billDto.targetCurrency());
        Double discount = discountService.calculateDiscount(billDto);
        Double netAmount = billDto.totalAmount() - discount;
        Double payableAmount = netAmount * exchangeRate;

        return new PayableAmountDto(
            payableAmount,
            discount,
            billDto.totalAmount(),
            exchangeRate,
            "USD",
            "PKR"
        );
    }
}
