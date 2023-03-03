package com.project.exchangerateapi.exchangerate.caller;

import com.project.exchangerateapi.exchangerate.dto.TarihDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Slf4j
public class TCMBCaller {
    private final RestTemplate restTemplate;
    @Value("${tcmb.api.today-url}")
    private String tcmbTodayUrl;
    @Value("${tcmb.api.date-url}")
    private String tcmbDateUrl;

    public TarihDate getExchangeRatesListToday() {
        try {
            return restTemplate.getForObject(tcmbTodayUrl, TarihDate.class);
        } catch (Exception e) {
            log.error("Data could not be found because the given date is weekend.");
        }
        return null;
    }

    public TarihDate getExchangeRateByDate(LocalDate date, Boolean isLogShow) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String yearandmonth = date.format(DateTimeFormatter.ofPattern("yyyyMM"));
        String url = tcmbDateUrl.replace("{date}", formattedDate).replace("{yearandmonth}", yearandmonth);

        try {
            return restTemplate.getForObject(url, TarihDate.class);
        } catch (Exception e) {
            if (isLogShow)
                log.error("Data could not be found because the given date is weekend. Date: {}", date);
        }
        return null;
    }


}
