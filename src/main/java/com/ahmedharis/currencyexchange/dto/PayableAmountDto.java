package com.ahmedharis.currencyexchange.dto;

public record PayableAmountDto(
    Double payableAmount,
    Double discountAmountBeforeConversion,
    Double totalAmountBeforeConversion,
    Double conversionRate,
    String sourceCurrency,
    String targetCurrency
) {
}
