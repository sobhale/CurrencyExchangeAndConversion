package com.hashedin.currencyconversionservice.controller;

import com.hashedin.currencyconversionservice.service.CurrencyConvertorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConversionControllerTest {

    @InjectMocks
    private CurrencyConversionController controller;
    @Mock
    private CurrencyConvertorService service;

    @Test
    void currencyConversion() {
        String expectedRes = "test";
        Mockito.when(service.convertCurrency(Mockito.anyString(),
                Mockito.anyString())).thenReturn(expectedRes);
        String actualRes = controller.currencyConversion("INR", "INR");
        assertEquals(expectedRes, actualRes);
    }
}