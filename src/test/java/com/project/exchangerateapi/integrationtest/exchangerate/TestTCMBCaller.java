package com.project.exchangerateapi.integrationtest.exchangerate;


import com.project.exchangerateapi.exchangerate.caller.TCMBCaller;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTCMBCaller {
    @Autowired
    private TCMBCaller tcmbCaller;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetExchangeRatesListToday() {
        TarihDate expectedTarihDate = restTemplate.getForObject("https://www.tcmb.gov.tr/kurlar/today.xml", TarihDate.class);

        TarihDate tarihDate = tcmbCaller.getExchangeRatesListToday();

        assertNotNull(tarihDate);
        assertNotNull(expectedTarihDate);
        assertEquals(expectedTarihDate.getCurrencyList().size(), tarihDate.getCurrencyList().size());
        assertEquals(expectedTarihDate.getCurrencyList().get(0).getCurrencyCode(), tarihDate.getCurrencyList().get(0).getCurrencyCode());
        assertEquals(expectedTarihDate.getCurrencyList().get(0).getForexBuying(), tarihDate.getCurrencyList().get(0).getForexBuying());
    }

    @Test
    public void testGetExchangeRateByDate() {
        LocalDate date = LocalDate.of(2023, 02, 16);

        TarihDate expectedTarihDate = restTemplate.getForObject("https://www.tcmb.gov.tr/kurlar/202302/16022023.xml", TarihDate.class);

        TarihDate tarihDate = tcmbCaller.getExchangeRateByDate(date, false);

        assertNotNull(tarihDate);
        assertNotNull(expectedTarihDate);
        assertEquals(expectedTarihDate.getCurrencyList().size(), tarihDate.getCurrencyList().size());
        assertEquals(expectedTarihDate.getCurrencyList().get(0).getCurrencyCode(), tarihDate.getCurrencyList().get(0).getCurrencyCode());
        assertEquals(expectedTarihDate.getCurrencyList().get(0).getForexBuying(), tarihDate.getCurrencyList().get(0).getForexBuying());
    }

}
