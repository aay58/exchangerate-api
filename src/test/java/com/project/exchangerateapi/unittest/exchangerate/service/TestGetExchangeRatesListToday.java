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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        tarihDate = TestUtil.createTarihDate();
    }

    @Test
    public void testGetExchangeRatesListToday() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(tarihDate);

        List<Currency> currencyList = exchangeRateService.getExchangeRatesListToday();
        assertEquals(currencyList.size(), 2);
        TestUtil.assertEqualsCurrency(currencyList.get(0), tarihDate.getCurrencyList().get(0));
        TestUtil.assertEqualsCurrency(currencyList.get(1), tarihDate.getCurrencyList().get(1));

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    @Test
    public void testGetExchangeRatesListTodayResponseNull() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(null);

        List<Currency> currencyList = exchangeRateService.getExchangeRatesListToday();
        assertNull(currencyList);

        verify(tcmbCaller).getExchangeRatesListToday();
    }

}
