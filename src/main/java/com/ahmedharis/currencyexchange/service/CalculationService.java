package com.ahmedharis.currencyexchange.service;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;

public interface CalculationService {
  PayableAmountDto calculatePayableAmount(BillDto billDto);
}
