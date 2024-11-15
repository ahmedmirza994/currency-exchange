package com.ahmedharis.currencyexchange.dto;

import com.ahmedharis.currencyexchange.enums.ItemCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemDto(
    @NotNull(message = "Name must not be null.")
    String name,

    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be positive.")
    Double price,

    @NotNull(message = "Quantity must not be null.")
    @Positive(message = "Quantity must be positive.")
    Integer quantity,

    @NotNull(message = "Category must not be null.")
    ItemCategory category
) {
    public Double totalAmount() {
        return price() * quantity();
    }
}
