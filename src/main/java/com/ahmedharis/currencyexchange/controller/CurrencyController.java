package com.ahmedharis.currencyexchange.controller;

import com.ahmedharis.currencyexchange.dto.ApiResponse;
import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;
import com.ahmedharis.currencyexchange.service.CalculationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {
  private final CalculationService calculationService;

  public CurrencyController(CalculationService calculationService) {
    this.calculationService = calculationService;
  }

  @PostMapping("/calculate")
  public ApiResponse<PayableAmountDto> calculate(@Valid @RequestBody BillDto billDto) {
    PayableAmountDto dto = calculationService.calculatePayableAmount(billDto);
    return ApiResponse.success(dto);
  }
}
