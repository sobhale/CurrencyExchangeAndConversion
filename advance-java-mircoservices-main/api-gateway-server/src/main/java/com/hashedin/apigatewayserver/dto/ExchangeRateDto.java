package com.hashedin.apigatewayserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDto {

    private int id;
    private String currencyCode;
    private double exchangeRate;

}
