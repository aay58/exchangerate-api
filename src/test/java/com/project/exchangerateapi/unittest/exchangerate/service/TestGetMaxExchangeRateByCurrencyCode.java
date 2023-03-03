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

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class TestGetMaxExchangeRateByCurrencyCode {

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Mock
    private TCMBCaller tcmbCaller;

    private TarihDate tarihDate;

    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void beforeEach() {
        tarihDate = TestUtil.createTarihDate();
        startDate = LocalDate.of(2023,02,10);

    }
    @Test
    public void testGetMaxExchangeRateByCurrencyCode() {
        endDate = LocalDate.of(2023,03,10);

        TarihDate tarihDateEndDate = TestUtil.createTarihDate();
        tarihDateEndDate.getCurrencyList().get(0).setForexSelling(new BigDecimal("20.00"));

        when(tcmbCaller.getExchangeRateByDate(startDate, false)).thenReturn(tarihDate);
        when(tcmbCaller.getExchangeRateByDate(endDate, false)).thenReturn(tarihDateEndDate);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals(maxRate.toString(), "20.00");

        verify(tcmbCaller).getExchangeRateByDate(startDate, false);
        verify(tcmbCaller).getExchangeRateByDate(endDate, false);
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCodeStartDateHigh() {
        endDate = LocalDate.of(2023,03,10);

        TarihDate tarihDateEndDate = TestUtil.createTarihDate();
        tarihDateEndDate.getCurrencyList().get(0).setForexSelling(new BigDecimal("15.00"));

        when(tcmbCaller.getExchangeRateByDate(startDate, false)).thenReturn(tarihDate);
        when(tcmbCaller.getExchangeRateByDate(endDate, false)).thenReturn(tarihDateEndDate);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals(maxRate.toString(), "18.8991");

        verify(tcmbCaller).getExchangeRateByDate(startDate, false);
        verify(tcmbCaller).getExchangeRateByDate(endDate, false);
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCodeStartEndDateSame() {
        endDate = LocalDate.of(2023,02,10);

        when(tcmbCaller.getExchangeRateByDate(startDate, false)).thenReturn(tarihDate);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals(maxRate.toString(), "18.8991");

        verify(tcmbCaller).getExchangeRateByDate(startDate, false);
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCodeNotEnterWhile() {
        endDate = LocalDate.of(2023,01,10);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals(maxRate.toString(), "0");
    }

    @Test
    public void testGetExchangeRatesListTodayByCurrencyCodeNotIncludedWantedCode() {
        when(tcmbCaller.getExchangeRatesListToday()).thenReturn(tarihDate);

        Currency currency = exchangeRateService.getExchangeRatesListTodayByCurrencyCode("DKK");
        assertNull(currency);

        verify(tcmbCaller).getExchangeRatesListToday();
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCodeTarihDateNull() {
        endDate = LocalDate.of(2023,03,10);

        when(tcmbCaller.getExchangeRateByDate(startDate, false)).thenReturn(null);
        when(tcmbCaller.getExchangeRateByDate(endDate, false)).thenReturn(tarihDate);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals("18.8991", maxRate.toString());

        verify(tcmbCaller).getExchangeRateByDate(startDate, false);
        verify(tcmbCaller).getExchangeRateByDate(endDate, false);
    }

    @Test
    public void testGetMaxExchangeRateByCurrencyCodeAllTarihDateNull() {
        endDate = LocalDate.of(2023,03,10);

        when(tcmbCaller.getExchangeRateByDate(startDate, false)).thenReturn(null);
        when(tcmbCaller.getExchangeRateByDate(endDate, false)).thenReturn(null);

        BigDecimal maxRate = exchangeRateService.getMaxExchangeRateByCurrencyCode(startDate, endDate, "USD");
        assertNotNull(maxRate);
        assertEquals("0", maxRate.toString());

        verify(tcmbCaller).getExchangeRateByDate(startDate, false);
        verify(tcmbCaller).getExchangeRateByDate(endDate, false);
    }
}
