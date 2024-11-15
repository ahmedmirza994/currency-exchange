package com.ahmedharis.currencyexchange.dto;

import com.ahmedharis.currencyexchange.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserDto(
    @NotNull(message = "User type must not be null.")
    UserType userType,
    @Positive(message = "Tenure must be positive.")
    Integer tenure // in years
) {
}
