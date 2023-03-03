package com.project.exchangerateapi.unittest.exchangerate.caller;

import com.project.exchangerateapi.common.TestUtil;
import com.project.exchangerateapi.exchangerate.caller.TCMBCaller;
import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(SpringExtension.class)
public class TestGetExchangeRateByDate {

    @InjectMocks
    private TCMBCaller tcmbCaller;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Logger log;

    private LocalDate date;

    @BeforeEach
    public void beforeEach() {
        date = LocalDate.of(2023, 02, 16);
        ReflectionTestUtils.setField(tcmbCaller, "tcmbDateUrl", "https://www.tcmb.gov.tr/kurlar/today.xml");
    }
    @Test
    public void testGetExchangeRateByDate() {
        LocalDate date = LocalDate.of(2023, 02, 16);
        TarihDate expected = TestUtil.createTarihDate();
        when(restTemplate.getForObject(anyString(), eq(TarihDate.class))).thenReturn(expected);

        TarihDate result = tcmbCaller.getExchangeRateByDate(date, false);

        assertEquals(expected, result);
        assertEquals(expected.getCurrencyList().size(), result.getCurrencyList().size());
        assertEquals(expected.getCurrencyList().get(0).getCurrencyCode(), result.getCurrencyList().get(0).getCurrencyCode());

        verify(restTemplate).getForObject(anyString(), eq(TarihDate.class));
    }

    @Test
    public void testGetExchangeRateByDateException() {
        when(restTemplate.getForObject(anyString(), eq(TarihDate.class))).thenThrow(new RestClientException(""));

        TarihDate result = tcmbCaller.getExchangeRateByDate(date, false);

        assertNull(result);
        verify(restTemplate).getForObject(anyString(), eq(TarihDate.class));

    }
}
