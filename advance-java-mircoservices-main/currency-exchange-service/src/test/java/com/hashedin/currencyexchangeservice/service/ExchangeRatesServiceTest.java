package com.hashedin.currencyexchangeservice.service;

import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import com.hashedin.currencyexchangeservice.repository.ExchangeRatesRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceTest {

    @InjectMocks
    private ExchangeRatesService service;

    @Mock
    private ExchangeRatesRepo repository;

    @Test
    void fetchAllCurrencyRatesTest() {
        List<ExchangeRate> expectedRates = getExchangeRates();
        Mockito.when(repository.findAll()).thenReturn(expectedRates);
        List<ExchangeRate> actualRates = service.fetchAllCurrencyRates();
        assertEquals(expectedRates.size(), actualRates.size());
        assertEquals(expectedRates.get(0).getExchangeRate(), actualRates.get(0).getExchangeRate());
    }

    @Test
    void fetchCurrencyRateTest() {

        ExchangeRate expectedRate = getExchangeRate();
        Mockito.when(repository.findByCurrencyCode(Mockito.anyString())).thenReturn(expectedRate);
        ExchangeRate actualRate = service.fetchCurrencyRate("INR");
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());
    }

    @Test
    void updateExchangeRateTest() {


        ExchangeRate expectedRate = getExchangeRate();
        when(repository.save(Mockito.any())).thenReturn(expectedRate);
        when(repository.findByCurrencyCode(Mockito.anyString())).thenReturn(expectedRate);
        ExchangeRate actualRate = service.updateExchangeRate("INR", new ExchangeRate());
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());
    }

    @Test
    void addExchangeRate() {
        ExchangeRate expectedRate = getExchangeRate();
        when(repository.save(Mockito.any())).thenReturn(expectedRate);
        ExchangeRate actualRate = service.addExchangeRate(new ExchangeRate());
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());
    }

    @Test
    void deleteCurrencyRate() {
        String expectedResult = "Deleted Exchange Rate with code: INR";
        ExchangeRate expectedRate = getExchangeRate();
        doNothing().when(repository).delete(Mockito.any());
        when(repository.findByCurrencyCode(Mockito.anyString())).thenReturn(expectedRate);
        String actualResult = service.deleteCurrencyRate("INR");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void saveAll() {
        List<ExchangeRate> expectedRates = getExchangeRates();
        Mockito.when(repository.saveAll(Mockito.any())).thenReturn(expectedRates);
        List<ExchangeRate> actualRates = service.saveAll(expectedRates);
        assertEquals(expectedRates.size(), actualRates.size());
        assertEquals(expectedRates.get(0).getExchangeRate(), actualRates.get(0).getExchangeRate());
    }

    private List<ExchangeRate> getExchangeRates(){
        ExchangeRate exchangeRate = getExchangeRate();


        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(exchangeRate);

        return rates;
    }

    private ExchangeRate getExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRate(10d);
        exchangeRate.setCurrencyCode("INR");
        exchangeRate.setId(1);
        return exchangeRate;
    }
}