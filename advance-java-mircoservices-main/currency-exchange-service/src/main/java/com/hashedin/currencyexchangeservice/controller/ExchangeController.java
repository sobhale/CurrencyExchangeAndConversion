package com.hashedin.currencyexchangeservice.controller;


import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import com.hashedin.currencyexchangeservice.service.ExchangeRatesService;
import com.hashedin.currencyexchangeservice.utils.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job importUserJob;

    /**
     * Fetches all the available exchange rates
     * @return List<ExchangeRate>
     */
    @GetMapping("/")
    public List<ExchangeRate> fetchAllCurrencyRates(){
        LOGGER.info("Fetching all exchange rates");
        return exchangeRatesService.fetchAllCurrencyRates();
    }

    /**
     * Returns exchange rate for single currency code
     * @param currCode
     * @return ExchangeRate
     */
    @GetMapping("/{currCode}")
    public ExchangeRate fetchCurrencyRate(@PathVariable  String currCode){
        LOGGER.info("Fetching exchange rate for Currency Code: " + currCode);
        exchangeRatesService.checkIfValidCurrency(currCode);
        return exchangeRatesService.fetchCurrencyRate(currCode);
    }

    /**
     * Saves exchange rate into the system/DB
     * @param exchangeRate
     * @return ExchangeRate
     */
    @PostMapping("/")
    public ExchangeRate addExchangeRate(@RequestBody ExchangeRate exchangeRate){
        LOGGER.info("Adding exchange rate for Currency Code: " + exchangeRate.getCurrencyCode());
        exchangeRatesService.checkIfValidCurrency(exchangeRate.getCurrencyCode());
        return exchangeRatesService.addExchangeRate(exchangeRate);
    }

    /**
     * Updates or saves exchange rate
     * @param currCode
     * @param exchangeRate
     * @return ExchangeRate
     */
    @PutMapping("/{currCode}")
    public ExchangeRate updateExchangeRate(@PathVariable String currCode,
                                           @RequestBody ExchangeRate exchangeRate){
        LOGGER.info("Updating exchange rate for Currency Code: " + currCode);
        exchangeRatesService.checkIfValidCurrency(currCode);
        exchangeRatesService.checkIfValidCurrency(exchangeRate.getCurrencyCode());
        return exchangeRatesService.updateExchangeRate(currCode, exchangeRate);
    }

    /**
     * Delete one exchange rate
     * @param currCode
     * @return
     */
    @DeleteMapping("/{currCode}")
    public String deleteCurrencyRate(@PathVariable  String currCode){
        LOGGER.info("Deleting exchange rate for Currency Code: " + currCode);
        exchangeRatesService.checkIfValidCurrency(currCode);
        return exchangeRatesService.deleteCurrencyRate(currCode);
    }

    /**
     *Adds exchanges rates from a csv input file
     * @param file
     * @return List<ExchangeRate>
     * @throws IOException
     */
    @PostMapping(value = "/addOrUpdateRates")
    public List<ExchangeRate> uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.info("Saving exchange rate from csv with name: " + file.getName());
        return exchangeRatesService.saveAll(CsvUtils.read(ExchangeRate.class, file.getInputStream()));
    }


    /**
     * Batch Job to trigger csv import of exchange rates
     * @param fileToImport
     * @throws Exception
     */
    //TODO: Test this Batch Job
    @RequestMapping(value = "/addOrUpdateRates", consumes = "multipart/form-data")
    public void handle(@RequestParam("file") File fileToImport) throws Exception{
        LOGGER.info("Running Batch Job to save exchange rate from csv with name: " + fileToImport.getName());
        jobLauncher.run(importUserJob, new JobParametersBuilder()
                .addString("fullPathFileName", fileToImport.getAbsolutePath())
                .toJobParameters());
    }

}
