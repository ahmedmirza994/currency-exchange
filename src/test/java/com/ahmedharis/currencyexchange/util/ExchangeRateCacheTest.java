package com.ahmedharis.currencyexchange.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExchangeRateCacheTest {

  private ExchangeRateCache exchangeRateCache;

  @BeforeEach
  void setUp() {
    exchangeRateCache = ExchangeRateCache.getInstance();
  }

  @Test
  void putAndGetExchangeRate() {
    exchangeRateCache.put("USD_EUR", 0.85);
    Double rate = exchangeRateCache.get("USD_EUR");
    assertNotNull(rate);
    assertEquals(0.85, rate);
  }

  @Test
  void getExchangeRateWhenNotPresent() {
    Double rate = exchangeRateCache.get("USD_PKR");
    assertNull(rate);
  }

  @Test
  void putAndGetMultipleExchangeRates() {
    exchangeRateCache.put("USD_EUR", 0.85);
    exchangeRateCache.put("USD_GBP", 0.75);
    Double rateEur = exchangeRateCache.get("USD_EUR");
    Double rateGbp = exchangeRateCache.get("USD_GBP");
    assertNotNull(rateEur);
    assertNotNull(rateGbp);
    assertEquals(0.85, rateEur);
    assertEquals(0.75, rateGbp);
  }
}
