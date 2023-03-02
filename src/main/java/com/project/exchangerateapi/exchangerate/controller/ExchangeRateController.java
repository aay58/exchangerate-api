package com.project.exchangerateapi.exchangerate.controller;

import com.project.exchangerateapi.exchangerate.dto.Currency;
import com.project.exchangerateapi.exchangerate.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/getExchangeRatesListToday")
    public List<Currency> getExchangeRatesListToday() {
        return exchangeRateService.getExchangeRatesListToday();
    }

    @GetMapping("/getExchangeRatesListTodayByCurrencyCode/{code}")
    public Currency getExchangeRatesListTodayByCurrencyCode(@PathVariable String code) {
        return exchangeRateService.getExchangeRatesListTodayByCurrencyCode(code);
    }
    @GetMapping("/getExchangeRateByDateAndCurrencyCode")
    public Currency getExchangeRateByDateAndCurrencyCode(@RequestParam("date") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
                                                         @RequestParam String currencyCode) {
        return exchangeRateService.getExchangeRateByDateAndCurrencyCode(date, currencyCode);
    }

    @GetMapping("/getMaxExchangeRateByCurrencyCode")
    public BigDecimal getExchangeRateByCurrencyCode(@RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
                                                    @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
                                                    @RequestParam String currencyCode) {
        return exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, currencyCode);
    }

}


