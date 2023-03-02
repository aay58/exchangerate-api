package com.project.exchangerateapi.exchangerate.service;

import com.project.exchangerateapi.exchangerate.caller.TCMBCaller;
import com.project.exchangerateapi.exchangerate.dto.Currency;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class ExchangeRateService {
    private final RestTemplate restTemplate;
    private final TCMBCaller tcmbCaller;

    public List<Currency> getExchangeRatesListToday() {
        TarihDate tarihDate = tcmbCaller.getExchangeRatesListToday();
        return tarihDate != null ? tarihDate.getCurrencyList() : null;
    }

    public Currency getExchangeRatesListTodayByCurrencyCode(String code) {
        TarihDate tarihDate = tcmbCaller.getExchangeRatesListToday();
        return tarihDate != null ? tarihDate.getCurrencyList().stream().filter(currency -> currency.getCurrencyCode().equals(code)).findFirst().orElse(null) : null;
    }

    public Currency getExchangeRateByDateAndCurrencyCode(LocalDate date, String currencyCode) {
        TarihDate response = tcmbCaller.getExchangeRateByDate(date, true);
        return response == null ? null : response.getCurrencyList().stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode)).findFirst().orElse(null);
    }

    public BigDecimal getMaxExchangeRateByCurrencyCode(LocalDate startDate, LocalDate endDate, String currencyCode) {
        BigDecimal highestRate = BigDecimal.ZERO;

        while (startDate.isBefore(endDate.plusDays(1))) {
            TarihDate tarihDate = tcmbCaller.getExchangeRateByDate(startDate, false);
            if (tarihDate != null) {
                Currency response = tarihDate.getCurrencyList().stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode)).findFirst().orElse(null);
                BigDecimal rate = response.getForexSelling();
                if (rate.compareTo(highestRate) > 0) {
                    highestRate = rate;
                }
            }
            startDate = startDate.plusDays(1);
        }
        return highestRate;
    }
}
