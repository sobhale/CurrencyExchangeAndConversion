package com.hashedin.currencyconversionservice.service;

import com.hashedin.currencyconversionservice.dto.ExchangeRateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Currency;

@Service
public class CurrencyConvertorService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConvertorService.class);

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Converts source currency value into dest currency value
     * @param sourceCurrCode
     * @param destCurrCode
     * @return String
     */
    public String convertCurrency(String sourceCurrCode, String destCurrCode) {

        try {
            Double sourceRate = getExchangeRate(sourceCurrCode);
            Double destRate = getExchangeRate(destCurrCode);

            checkIfValidCurrency(sourceCurrCode);
            checkIfValidCurrency(destCurrCode);

            Currency sourceCurr = Currency.getInstance(sourceCurrCode);
            Currency destCurr = Currency.getInstance(destCurrCode);

            Double conversionRate = sourceRate / destRate;
            logger.info("Currency conversion completed..!!!");
            return "1 " + sourceCurr.getSymbol() + " = " + conversionRate + " " + destCurr.getSymbol();
        }
        catch (IllegalArgumentException ex){
            logger.error(ex.getMessage());
            throw new IllegalArgumentException("Pass valid currency code");
        }
    }

    private Double getExchangeRate(String currCode) {
        ExchangeRateDto exchangeRate = restTemplate
                .getForObject("http://currency-exchange-service/exchange/" + currCode, ExchangeRateDto.class);
        return exchangeRate.getExchangeRate();
    }

    public boolean checkIfValidCurrency(String currencyCode) {
        try{
            Currency currency = Currency.getInstance(currencyCode);
        }catch(IllegalArgumentException | NullPointerException ex){
            logger.error("Invalid Currency code {} passed...: ", currencyCode );
            throw new IllegalArgumentException("Pass valid currency code");
        }
        return true;
    }
}
