package com.ahmedharis.currencyexchange.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public record BillDto(
    @NotNull(message = "User must not be null.")
    UserDto user,

    @NotEmpty(message = "Items cannot be empty")
    @Size(min = 1)
    List<ItemDto> items,

    @NotNull(message = "Original currency cannot be null")
    @Size(min = 3, max = 3, message = "Original currency must be 3 characters")
    String originalCurrency,

    @NotNull(message = "Target currency cannot be null")
    @Size(min = 3, max = 3, message = "Target currency must be 3 characters")
    String targetCurrency
) {
    public BillDto {
        if (items == null) {
            items = new ArrayList<>();
        }
    }

    public Double totalAmount() {
        return items.stream()
            .mapToDouble(item -> item.price() * item.quantity())
            .sum();
    }
}
