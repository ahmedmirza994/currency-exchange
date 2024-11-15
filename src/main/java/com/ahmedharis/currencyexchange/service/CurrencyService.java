package com.ahmedharis.currencyexchange.service;

import org.springframework.stereotype.Service;

public interface CurrencyService {
    Double getExchangeRate(String fromCurrency, String toCurrency);
}

