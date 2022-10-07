package com.hashedin.currencyconversionservice.controller;

import com.hashedin.currencyconversionservice.service.CurrencyConvertorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversion")
public class CurrencyConversionController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyConvertorService currencyConvertorService;

    /**
     * Converts source currency value into dest currency value
     * @param sourceCurrCode
     * @param destCurrCode
     * @return String
     */
    @GetMapping("/{sourceCurrCode}/{destCurrCode}")
    public String currencyConversion(@PathVariable String sourceCurrCode,
                                     @PathVariable String destCurrCode){

        logger.info("Converting {} into {} currency", sourceCurrCode, destCurrCode);
            return currencyConvertorService.convertCurrency(sourceCurrCode, destCurrCode);
    }

}
