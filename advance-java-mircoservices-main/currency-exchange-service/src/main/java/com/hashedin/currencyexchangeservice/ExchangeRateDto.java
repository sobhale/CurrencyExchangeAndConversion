package com.hashedin.currencyexchangeservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDto {

    //private int id;
    private String currencyCode;
    private double exchangeRate;

}
