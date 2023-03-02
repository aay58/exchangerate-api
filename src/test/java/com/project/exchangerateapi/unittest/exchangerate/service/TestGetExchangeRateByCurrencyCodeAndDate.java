package com.project.exchangerateapi.unittest.exchangerate.service;

import com.project.exchangerateapi.common.TestEntityBuilder;
import com.project.exchangerateapi.exchangerate.caller.TCMBCaller;
import com.project.exchangerateapi.exchangerate.dto.Currency;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import com.project.exchangerateapi.exchangerate.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TestGetExchangeRateByCurrencyCodeAndDate {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private TCMBCaller tcmbCaller;

    private TarihDate tarihDate;
    private LocalDate date;

    @BeforeEach
    void beforeEach() {
        tarihDate = TestEntityBuilder.createTarihDate();
        date = LocalDate.of(2023,03,10);
    }

    @Test
    public void testGetExchangeRateByCurrencyCodeAndDate() {
        when(tcmbCaller.getExchangeRateByDate(date, true)).thenReturn(tarihDate);

        Currency currency = exchangeRateService.getExchangeRateByDateAndCurrencyCode(date, "USD");
        assertNotNull(currency);
        assertEqualsCurrency(currency, tarihDate.getCurrencyList().get(0));

        verify(tcmbCaller).getExchangeRateByDate(date, true);
    }
    @Test
    public void testGetExchangeRateByCurrencyCodeAndDateResponseNull() {
        when(tcmbCaller.getExchangeRateByDate(date, true)).thenReturn(null);

        Currency currency = exchangeRateService.getExchangeRateByDateAndCurrencyCode(date, "USD");
        assertNull(currency);

        verify(tcmbCaller).getExchangeRateByDate(date, true);
    }
    @Test
    public void testGetExchangeRateByCurrencyCodeAndDateCodeNotIncludedWantedCode() {
        when(tcmbCaller.getExchangeRateByDate(date, true)).thenReturn(tarihDate);

        Currency currency = exchangeRateService.getExchangeRateByDateAndCurrencyCode(date, "DDK");
        assertNull(currency);

        verify(tcmbCaller).getExchangeRateByDate(date, true);
    }

    private void assertEqualsCurrency(Currency currency, Currency createdCurrency) {
        assertEquals(currency.getCurrencyCode(),    createdCurrency.getCurrencyCode());
        assertEquals(currency.getIsim(),            createdCurrency.getIsim());
        assertEquals(currency.getCurrencyName(),    createdCurrency.getCurrencyName());
        assertEquals(currency.getForexBuying(),     createdCurrency.getForexBuying());
        assertEquals(currency.getForexSelling(),    createdCurrency.getForexSelling());
        assertEquals(currency.getBanknoteSelling(), createdCurrency.getBanknoteSelling());
        assertEquals(currency.getBanknoteBuying(),  createdCurrency.getBanknoteBuying());

    }


}
