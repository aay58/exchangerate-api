package com.project.exchangerateapi.unittest.exchangerate.service;

import com.project.exchangerateapi.common.TestUtil;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TestGetExchangeRatesListTodayByCurrencyCode {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private TCMBCaller tcmbCaller;

    private TarihDate tarihDate;

    @BeforeEach
    void beforeEach() {
        tarihDate = TestUtil.createTarihDate();
    }
    @Test
    public void testGetExchangeRatesListTodayByCurrencyCode() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(tarihDate);

        Currency currency = exchangeRateService.getExchangeRatesListTodayByCurrencyCode("USD");
        assertNotNull(currency);
        TestUtil.assertEqualsCurrency(currency, tarihDate.getCurrencyList().get(0));

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    @Test
    public void testGetExchangeRatesListTodayByCurrencyCodeResponseNull() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(null);

        Currency currency = exchangeRateService.getExchangeRatesListTodayByCurrencyCode("USD");
        assertNull(currency);

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    @Test
    public void testGetExchangeRatesListTodayByCurrencyCodeNotIncludedWantedCode() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(tarihDate);

        Currency currency = exchangeRateService.getExchangeRatesListTodayByCurrencyCode("DKK");
        assertNull(currency);

        verify(tcmbCaller).getExchangeRatesListToday();
    }

}
