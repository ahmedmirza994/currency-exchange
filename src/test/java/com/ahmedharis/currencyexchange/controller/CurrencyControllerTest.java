package com.ahmedharis.currencyexchange.controller;

import com.ahmedharis.currencyexchange.dto.BillDto;
import com.ahmedharis.currencyexchange.dto.ItemDto;
import com.ahmedharis.currencyexchange.dto.PayableAmountDto;
import com.ahmedharis.currencyexchange.dto.UserDto;
import com.ahmedharis.currencyexchange.enums.ItemCategory;
import com.ahmedharis.currencyexchange.enums.UserType;
import com.ahmedharis.currencyexchange.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalculationService calculationService;

    @InjectMocks
    private CurrencyController currencyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
    }

    @Test
    void calculateReturnsPayableAmount() throws Exception {
        UserDto user = new UserDto(UserType.ADMIN, 5);
        List<ItemDto> items = List.of(new ItemDto("Item1", 10.0, 2, ItemCategory.CLOTHING));
        BillDto bill = new BillDto(user, items, "US", "EUR");

        PayableAmountDto payableAmountDto = new PayableAmountDto(
            100.0, 0.0, 100.0, 1.0, "EUR", "USD"
        );

        when(calculationService.calculatePayableAmount(any(BillDto.class))).thenReturn(payableAmountDto);

        mockMvc.perform(post("/api/calculate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getBody()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.payableAmount").value(100.0));
    }

    @Test
    void calculateReturnsBadRequestForInvalidInput() throws Exception {
        mockMvc.perform(post("/api/calculate")
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
            """.trim();
    }
}
