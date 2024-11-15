package com.ahmedharis.currencyexchange.service;

import com.ahmedharis.currencyexchange.dto.BillDto;

public interface DiscountService {
    Double calculateDiscount(BillDto billDto);
}
