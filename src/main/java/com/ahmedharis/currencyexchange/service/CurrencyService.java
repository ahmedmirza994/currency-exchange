package com.ahmedharis.currencyexchange.service;

public interface CurrencyService {
  Double getExchangeRate(String fromCurrency, String toCurrency);
}
