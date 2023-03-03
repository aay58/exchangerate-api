package com.project.exchangerateapi.integrationtest.exchangerate;


import com.project.exchangerateapi.exchangerate.dto.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestExchangeRateController {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetExchangeRatesListToday(){

        //if today is not weekend
        List<Currency> currencyList = this.restTemplate.getForObject("http://localhost:" + port + "/api/getExchangeRatesListToday", List.class);
        assertNotNull(currencyList);
    }

    @Test
    public void testGetExchangeRatesListTodayByCurrencyCode(){
        //if today is not weekend
        Currency currency = this.restTemplate.getForObject("http://localhost:" + port + "/api/getExchangeRatesListTodayByCurrencyCode/USD", Currency.class);

        assertNotNull(currency);
        assertEquals(currency.getIsim(), "ABD DOLARI");
        assertEquals(currency.getCurrencyCode(), "USD");
    }

    @Test
    public void testGetExchangeRateByDateAndCurrencyCode(){
        String date = "16/02/2023";
        String url = "http://localhost:" + port + "/api/getExchangeRateByDateAndCurrencyCode?date=" + date + "&currencyCode=USD" ;

        Currency currency = this.restTemplate.getForObject(url, Currency.class);

        assertNotNull(currency);
        assertEquals(currency.getIsim(), "ABD DOLARI");
        assertEquals(currency.getCurrencyCode(), "USD");
        assertEquals(currency.getForexBuying().toString(), "18.8320");
        assertEquals(currency.getBanknoteBuying().toString(),  "18.8188");
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCode(){
        String startDate = "16/02/2023";
        String endDate = "17/02/2023";
        String url = "http://localhost:" + port + "/api/getMaxExchangeRateByCurrencyCode?startDate=" + startDate + "&endDate=" + endDate + "&currencyCode=USD" ;

        BigDecimal maxRate = this.restTemplate.getForObject(url, BigDecimal.class);

        assertEquals(maxRate.toString(), "18.8700");
    }

}
