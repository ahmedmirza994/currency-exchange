package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.client.ExchangeRateClient;
import com.ahmedharis.currencyexchange.service.CurrencyService;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final ExchangeRateClient exchangeRateClient;

    public CurrencyServiceImpl(ExchangeRateClient exchangeRateClient) {
        this.exchangeRateClient = exchangeRateClient;
    }

    @Override
    public Double getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRateClient.getConversionRate(fromCurrency, toCurrency);
    }
}
