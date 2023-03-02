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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TestGetExchangeRatesListToday {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private TCMBCaller tcmbCaller;

    private TarihDate tarihDate;

    @BeforeEach
    void beforeEach() {
        tarihDate = TestEntityBuilder.createTarihDate();
    }

    @Test
    public void testGetExchangeRatesListToday() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(tarihDate);

        List<Currency> currencyList = exchangeRateService.getExchangeRatesListToday();
        assertEquals(currencyList.size(), 2);
        assertEqualsCurrency(currencyList.get(0), tarihDate.getCurrencyList().get(0));
        assertEqualsCurrency(currencyList.get(1), tarihDate.getCurrencyList().get(1));

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    @Test
    public void testGetExchangeRatesListTodayResponseNull() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(null);

        List<Currency> currencyList = exchangeRateService.getExchangeRatesListToday();
        assertNull(currencyList);

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    private void assertEqualsCurrency(Currency currency, Currency createdCurrency) {
        assertEquals(currency.getCurrencyCode(), createdCurrency.getCurrencyCode());
        assertEquals(currency.getIsim(), createdCurrency.getIsim());
        assertEquals(currency.getCurrencyName(), createdCurrency.getCurrencyName());
        assertEquals(currency.getForexBuying(), createdCurrency.getForexBuying());
        assertEquals(currency.getForexSelling(), createdCurrency.getForexSelling());
        assertEquals(currency.getBanknoteSelling(), createdCurrency.getBanknoteSelling());
        assertEquals(currency.getBanknoteBuying(), createdCurrency.getBanknoteBuying());

    }

}
