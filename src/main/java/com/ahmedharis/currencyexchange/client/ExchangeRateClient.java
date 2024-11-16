package com.ahmedharis.currencyexchange.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ExchangeRateClient {

    @Value("${exchangerate.api.key}")
    private String apiKey;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";

    public ExchangeRateClient(OkHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public Double getConversionRate(String originalCurrency, String targetCurrency) {
        String url = String.format("%s/%s/pair/%s/%s", BASE_URL, apiKey, originalCurrency, targetCurrency);
        Request request = new Request.Builder()
            .url(url)
            .build();

        try (var response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to fetch exchange rate");
            }
            var jsonResponse = objectMapper.readValue(response.body().string(), Map.class);
            return (double) jsonResponse.get("conversion_rate");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
