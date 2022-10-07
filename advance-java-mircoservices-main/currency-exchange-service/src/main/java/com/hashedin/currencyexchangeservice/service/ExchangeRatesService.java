package com.hashedin.currencyexchangeservice.service;

import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import com.hashedin.currencyexchangeservice.repository.ExchangeRatesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class ExchangeRatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRatesService.class);

    @Autowired
    private ExchangeRatesRepo exchangeRateRepo;

    /**
     * Fetches all the available exchange rates
     * @return
     */
    @Cacheable("exchangeRate")
    public List<ExchangeRate> fetchAllCurrencyRates(){
        return exchangeRateRepo.findAll();
    }

    /**
     * Returns exchange rate for single currency code
     * @param currCode
     * @return ExchangeRate
     */
    @Cacheable("exchangeRate")
    public ExchangeRate fetchCurrencyRate(String currCode) {
        ExchangeRate exchangeRate =  exchangeRateRepo.findByCurrencyCode(currCode.toUpperCase());

        if(exchangeRate == null){
            String msg = "No Currency found with code:" + currCode;
            LOGGER.error(msg);
            throw new ResourceNotFoundException(msg);
        }
        return exchangeRate;
    }

    /**
     * Updates or saves exchange rate
     * @param currCode
     * @param exchangeRate
     * @return ExchangeRate
     */
    public ExchangeRate updateExchangeRate(String currCode, ExchangeRate exchangeRate) {

        ExchangeRate exchangeRateFromDb = exchangeRateRepo.findByCurrencyCode(currCode.toUpperCase());
        if(exchangeRateFromDb != null){
            exchangeRateFromDb.setExchangeRate(exchangeRate.getExchangeRate());
            exchangeRateFromDb.setCurrencyCode(exchangeRate.getCurrencyCode().toUpperCase());
            return exchangeRateRepo.save(exchangeRateFromDb);
        }
        else{
            return exchangeRateRepo.save(exchangeRate);
        }

    }

    /**
     * Saves exchange rate into the system/DB
     * @param exchangeRate
     * @return ExchangeRate
     */
    public ExchangeRate addExchangeRate(ExchangeRate exchangeRate) {
        checkIfValidCurrency(exchangeRate.getCurrencyCode().toUpperCase());
        exchangeRate.setCurrencyCode(exchangeRate.getCurrencyCode().toUpperCase());
        return exchangeRateRepo.save(exchangeRate);
    }

    /**
     * Delete one exchange rate
     * @param currCode
     * @return String
     */
    public String deleteCurrencyRate(String currCode) {
        ExchangeRate exchangeRateFromDb = fetchCurrencyRate(currCode);
        exchangeRateRepo.delete(exchangeRateFromDb);
        return "Deleted Exchange Rate with code: " + currCode;
    }

    /**
     * Saves list of exchange rates
     * @param exchangeRates
     * @return List<ExchangeRate>
     */
    public List<ExchangeRate> saveAll(List<ExchangeRate> exchangeRates) {
        LOGGER.info("Saving {} exchange rates from Csv file", exchangeRates.size());
        return exchangeRateRepo.saveAll(exchangeRates);
    }

    public boolean checkIfValidCurrency(String currencyCode) {
        try{
            Currency currency = Currency.getInstance(currencyCode.toUpperCase());
        }catch(IllegalArgumentException | NullPointerException ex){
            LOGGER.error("Invalid Currency code {} passed...: ", currencyCode );
            throw new IllegalArgumentException("Pass valid currency code");
        }
        return true;
    }
}
