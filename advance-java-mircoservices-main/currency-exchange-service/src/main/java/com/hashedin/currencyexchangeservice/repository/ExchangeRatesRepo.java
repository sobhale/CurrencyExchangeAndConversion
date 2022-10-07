package com.hashedin.currencyexchangeservice.repository;


import com.hashedin.currencyexchangeservice.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRatesRepo extends JpaRepository<ExchangeRate, Integer> {

    ExchangeRate findByCurrencyCode(String currCode);
}
