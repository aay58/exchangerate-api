package com.project.exchangerateapi.unittest.exchangerate.caller;

import com.project.exchangerateapi.common.TestUtil;
import com.project.exchangerateapi.exchangerate.caller.TCMBCaller;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
public class TestGetExchangeRatesListToday {

    @InjectMocks
    private TCMBCaller tcmbCaller;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void beforeEach() {
        ReflectionTestUtils.setField(tcmbCaller, "tcmbTodayUrl", "https://www.tcmb.gov.tr/kurlar/today.xml");
    }
    @Test
    public void testGetExchangeRatesListToday() {
        TarihDate expected = TestUtil.createTarihDate();
        when(restTemplate.getForObject(anyString(), eq(TarihDate.class))).thenReturn(expected);

        TarihDate result = tcmbCaller.getExchangeRatesListToday();

        assertEquals(expected, result);
        assertEquals(expected.getCurrencyList().size(), result.getCurrencyList().size());
        assertEquals(expected.getCurrencyList().get(0).getCurrencyCode(), result.getCurrencyList().get(0).getCurrencyCode());

        verify(restTemplate).getForObject(anyString(), eq(TarihDate.class));
    }

    @Test
    public void testGetExchangeRatesListTodayException() {
        when(restTemplate.getForObject(anyString(), eq(TarihDate.class))).thenThrow(new RestClientException(""));

        TarihDate result = tcmbCaller.getExchangeRatesListToday();

        assertNull(result);
        verify(restTemplate).getForObject(anyString(), eq(TarihDate.class));

    }
}
