package com.hashedin.currencyexchangeservice.controller;

import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import com.hashedin.currencyexchangeservice.service.ExchangeRatesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeControllerTest {

    @Mock
    private ExchangeRatesService service;

    @InjectMocks
    private ExchangeController controller;

    @Mock
    JobLauncher jobLauncher;


    @Test
    void fetchAllCurrencyRatesTest() {

        List<ExchangeRate> expectedRates = getExchangeRates();
        when(service.fetchAllCurrencyRates()).thenReturn(expectedRates);
        List<ExchangeRate> actualRates = controller.fetchAllCurrencyRates();
        assertEquals(expectedRates.size(), actualRates.size());
        assertEquals(expectedRates.get(0).getExchangeRate(), actualRates.get(0).getExchangeRate());
    }

    @Test
    void fetchCurrencyRateTest() {
        ExchangeRate expectedRate = getExchangeRate();
        when(service.fetchCurrencyRate(Mockito.anyString())).thenReturn(expectedRate);
        ExchangeRate actualRate = controller.fetchCurrencyRate("INR");
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());

    }

    @Test
    void addExchangeRateTest() {

        ExchangeRate expectedRate = getExchangeRate();
        when(service.addExchangeRate(Mockito.any())).thenReturn(expectedRate);
        ExchangeRate actualRate = controller.addExchangeRate(new ExchangeRate());
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());
    }

    @Test
    void updateExchangeRateTest() {
        ExchangeRate expectedRate = getExchangeRate();
        when(service.updateExchangeRate(Mockito.any(), Mockito.any())).thenReturn(expectedRate);
        ExchangeRate actualRate = controller.updateExchangeRate("INR", new ExchangeRate());
        assertEquals(expectedRate.getExchangeRate(), actualRate.getExchangeRate());
    }

    @Test
    void deleteCurrencyRate() {

        String expectedResult = "Deleted Exchange Rate with code: INR";
        when(service.deleteCurrencyRate(Mockito.anyString())).thenReturn(expectedResult);
        String actualResult = controller.deleteCurrencyRate("INR");
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void handleTest() throws Exception {

        when(jobLauncher.run(Mockito.any(), Mockito.any())).thenReturn(new JobExecution(1l));
        controller.handle(ResourceUtils.getFile("classpath:application.properties"));
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