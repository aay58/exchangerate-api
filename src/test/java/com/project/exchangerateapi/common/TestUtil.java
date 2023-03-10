package com.project.exchangerateapi.common;

import com.project.exchangerateapi.exchangerate.dto.Currency;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UtilityClass

public class TestUtil {


    public TarihDate createTarihDate() {
        TarihDate tarihDate = new TarihDate();
        tarihDate.setCurrencyList(List.of(createCurrencyUSD(), createCurrencyAUD()));
        return tarihDate;
    }

    public Currency createCurrencyUSD() {
        Currency currency = new Currency();
        currency.setCurrencyCode("USD");
        currency.setIsim("ABD DOLARI");
        currency.setCurrencyName("US DOLLAR");
        currency.setForexBuying( new BigDecimal("18.8652"));
        currency.setForexSelling(new BigDecimal("18.8991"));
        currency.setBanknoteBuying(new BigDecimal("18.8519"));
        currency.setBanknoteSelling(new BigDecimal("18.9275"));
        return currency;
    }

    public Currency createCurrencyAUD() {
        Currency currency = new Currency();
        currency.setCurrencyCode("AUD");
        currency.setIsim("AVUSTRALYA DOLARI");
        currency.setCurrencyName("AUSTRALIAN DOLLAR");
        currency.setForexBuying(new BigDecimal("12.7365"));
        currency.setForexSelling(new BigDecimal("12.8195"));
        currency.setBanknoteBuying(new BigDecimal("12.6779"));
        currency.setBanknoteSelling(new BigDecimal("12.8965"));
        return currency;
    }

    public void assertEqualsCurrency(Currency currency, Currency expectedCurrency) {
        assertEquals(expectedCurrency.getCurrencyCode(), currency.getCurrencyCode());
        assertEquals(expectedCurrency.getIsim(), currency.getIsim());
        assertEquals(expectedCurrency.getCurrencyName(), currency.getCurrencyName());
        assertEquals(expectedCurrency.getForexBuying(), currency.getForexBuying());
        assertEquals(expectedCurrency.getForexSelling(), currency.getForexSelling());
        assertEquals(expectedCurrency.getBanknoteSelling(), currency.getBanknoteSelling());
        assertEquals(expectedCurrency.getBanknoteBuying(), currency.getBanknoteBuying());
    }
}
