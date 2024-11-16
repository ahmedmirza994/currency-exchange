package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.client.ExchangeRateClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class CurrencyServiceImplTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRate_validCurrencies() {
        when(exchangeRateClient.getConversionRate("USD", "EUR")).thenReturn(0.85);

        Double rate = currencyService.getExchangeRate("USD", "EUR");

        assertEquals(0.85, rate);
    }

    @Test
    void getExchangeRate_sameCurrency() {
        when(exchangeRateClient.getConversionRate("USD", "USD")).thenReturn(1.0);

        Double rate = currencyService.getExchangeRate("USD", "USD");

        assertEquals(1.0, rate);
    }

    @Test
    void getExchangeRate_invalidCurrency() {
        when(exchangeRateClient.getConversionRate("USD", "INVALID")).thenReturn(null);

        Double rate = currencyService.getExchangeRate("USD", "INVALID");

        assertEquals(null, rate);
    }
}
