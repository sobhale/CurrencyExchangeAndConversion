package com.hashedin.currencyconversionservice.service;

import com.hashedin.currencyconversionservice.dto.ExchangeRateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConvertorServiceTest {

    @InjectMocks
    private CurrencyConvertorService service;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void convertCurrency() {

        ExchangeRateDto exchangeRateDto = getExchangeRate();
        exchangeRateDto.getExchangeRate();
        exchangeRateDto.getCurrencyCode();
        exchangeRateDto.getId();
        String expected = "1 ₹ = 1.0₹";
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(exchangeRateDto);
        String actual = service.convertCurrency("INR", "INR");
        assertEquals(expected, actual);

    }

    @Test
    void convertCurrencyExceptionTest() {

        ExchangeRateDto exchangeRateDto = getExchangeRate();
        exchangeRateDto.getExchangeRate();
        exchangeRateDto.getCurrencyCode();
        exchangeRateDto.getId();
        String expected = "1 ₹ = 1.0₹";
        when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(exchangeRateDto);
        //String actual = service.convertCurrency("IND", "INR");
        //assertEquals(expected, actual);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> service.convertCurrency("IND", "INR"));
        assertTrue(thrown.getMessage().contains("Pass valid currency code"));
    }

    private ExchangeRateDto getExchangeRate() {
        ExchangeRateDto exchangeRate = new ExchangeRateDto();
        exchangeRate.setExchangeRate(10d);
        exchangeRate.setCurrencyCode("INR");
        exchangeRate.setId(1);
        return exchangeRate;
    }
}