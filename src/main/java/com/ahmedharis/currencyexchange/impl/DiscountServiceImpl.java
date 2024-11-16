package com.ahmedharis.currencyexchange.impl;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.enums.ItemCategory;
import com.ahmedharis.currencyexchange.enums.UserType;
import com.ahmedharis.currencyexchange.service.DiscountService;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {
  @Override
  public Double calculateDiscount(BillDto billDto) {
    double discount = 0.0;
    double totalAmount = billDto.totalAmount();
    UserType userType = billDto.user().userType();
    int tenure = billDto.user().tenure();

    double discountPercentage = 0.0;
    if (userType == UserType.EMPLOYEE) {
      discountPercentage = 0.3;
    } else if (userType == UserType.AFFILIATE) {
      discountPercentage = 0.1;
    } else if (tenure > 2) {
      discountPercentage = 0.05;
    }

    double nonGroceryTotal =
        billDto.items().stream()
            .filter(item -> item.category() != ItemCategory.GROCERY)
            .mapToDouble(item -> item.totalAmount())
            .sum();

    discount += nonGroceryTotal * discountPercentage;

    discount += (int) (totalAmount / 100) * 5;
    return discount;
  }
}
