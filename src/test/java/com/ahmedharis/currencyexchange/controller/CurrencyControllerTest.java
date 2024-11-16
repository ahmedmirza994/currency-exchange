package com.ahmedharis.currencyexchange.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;
import com.ahmedharis.currencyexchange.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CurrencyControllerTest {

  private MockMvc mockMvc;

  @Mock private CalculationService calculationService;

  @InjectMocks private CurrencyController currencyController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void calculateWithAdminUser() throws Exception {
    PayableAmountDto payableAmountDto = new PayableAmountDto(100.0, 0.0, 100.0, 1.0, "EUR", "USD");

    when(calculationService.calculatePayableAmount(any(BillDto.class)))
        .thenReturn(payableAmountDto);

    mockMvc
        .perform(
            post("/api/calculate")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBody()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payableAmount").value(100.0));
  }

  @Test
  @WithMockUser(
      username = "employee",
      roles = {"EMPLOYEE"})
  void calculateWithEmployeeUser() throws Exception {
    PayableAmountDto payableAmountDto = new PayableAmountDto(100.0, 0.0, 100.0, 1.0, "EUR", "USD");

    when(calculationService.calculatePayableAmount(any(BillDto.class)))
        .thenReturn(payableAmountDto);

    mockMvc
        .perform(
            post("/api/calculate")
                .with(httpBasic("employee", "employee123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBody()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payableAmount").value(100.0));
  }

  @Test
  @WithMockUser(
      username = "affiliate",
      roles = {"AFFILIATE"})
  void calculateWithAffiliateUser() throws Exception {
    PayableAmountDto payableAmountDto = new PayableAmountDto(100.0, 0.0, 100.0, 1.0, "EUR", "USD");

    when(calculationService.calculatePayableAmount(any(BillDto.class)))
        .thenReturn(payableAmountDto);

    mockMvc
        .perform(
            post("/api/calculate")
                .with(httpBasic("affiliate", "affiliate123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getBody()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.payableAmount").value(100.0));
  }

  @Test
  void calculateReturnsBadRequestForInvalidInput() throws Exception {
    mockMvc
        .perform(
            post("/api/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"invalidField\": \"value\"}"))
        .andExpect(status().isBadRequest());
  }

  private String getBody() {
    return """
            {
                "user": {
                    "userType": "EMPLOYEE",
                    "tenure": 4
                },
                "items": [
                    {
                        "name": "iPhone 15 Pro",
                        "price": 1250.0,
                        "quantity": 1,
                        "category": "ELECTRONICS"
                    },
                    {
                        "name": "Eggs",
                        "price": 25,
                        "quantity": 12,
                        "category": "GROCERY"
                    }
                ],
                "originalCurrency": "USD",
                "targetCurrency": "PKR"
            }
            """
        .trim();
  }
}
