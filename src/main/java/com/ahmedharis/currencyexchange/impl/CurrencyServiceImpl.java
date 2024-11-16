package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.client.ExchangeRateClient;
import com.ahmedharis.currencyexchange.service.CurrencyService;
import com.ahmedharis.currencyexchange.util.ExchangeRateCache;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {
  private final ExchangeRateClient exchangeRateClient;
  private final ExchangeRateCache exchangeRateCacheService;

  public CurrencyServiceImpl(ExchangeRateClient exchangeRateClient) {
    this.exchangeRateClient = exchangeRateClient;
    this.exchangeRateCacheService = ExchangeRateCache.getInstance();
  }

  @Override
  public Double getExchangeRate(String fromCurrency, String toCurrency) {
    String key = fromCurrency + "_" + toCurrency;
    Double rate = exchangeRateCacheService.get(key);
    if (rate == null) {
      rate = exchangeRateClient.getConversionRate(fromCurrency, toCurrency);
      exchangeRateCacheService.put(key, rate);
    }
    return rate;
  }
}
