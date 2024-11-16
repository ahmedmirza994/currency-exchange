package com.ahmedharis.currencyexchange.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExchangeRateCache {
  private static final long DEFAULT_TTL = 600_000; // 10 minutes
  private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
  private static ExchangeRateCache instance;

  private ExchangeRateCache() {}

  public static ExchangeRateCache getInstance() {
    if (instance == null) {
      instance = new ExchangeRateCache();
    }
    return instance;
  }

  /**
   * Put the exchange rate in the cache with the given key. Key is the currency pair, e.g.
   * "USD_EUR".
   */
  public void put(String key, Double rate) {
    cache.put(key, new CacheEntry(rate, System.currentTimeMillis() + DEFAULT_TTL));
  }

  /**
   * Get the exchange rate from the cache with the given key. Key is the currency pair, e.g.
   * "USD_EUR".
   */
  public Double get(String key) {
    CacheEntry entry = cache.get(key);
    if (entry == null || entry.isExpired()) {
      return null;
    }
    return entry.rate;
  }

  private record CacheEntry(Double rate, long expiryTime) {
    boolean isExpired() {
      return System.currentTimeMillis() > expiryTime;
    }
  }
}
