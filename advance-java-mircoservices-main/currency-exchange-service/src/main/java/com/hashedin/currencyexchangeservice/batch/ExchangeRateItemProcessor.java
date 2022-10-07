package com.hashedin.currencyexchangeservice.batch;

import com.hashedin.currencyexchangeservice.ExchangeRateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ExchangeRateItemProcessor implements ItemProcessor<ExchangeRateDto, ExchangeRateDto> {


    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeRateItemProcessor.class);

    @Override
    public ExchangeRateDto process(final ExchangeRateDto exchangeRateDto) throws Exception {


        //int id = exchangeRateDto.getId();
        String currencyCode = exchangeRateDto.getCurrencyCode().toUpperCase();
        Double exchangeRate = exchangeRateDto.getExchangeRate();

        ExchangeRateDto transformedExchangeRate = new ExchangeRateDto(currencyCode, exchangeRate);
        LOGGER.info("Converting ( {} ) into ( {} )", exchangeRateDto, transformedExchangeRate);

        return transformedExchangeRate;
    }

}
