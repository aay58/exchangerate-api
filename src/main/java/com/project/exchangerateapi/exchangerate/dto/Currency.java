package com.project.exchangerateapi.exchangerate.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    @JacksonXmlProperty(localName = "Isim")
    private String isim;

    @JacksonXmlProperty(localName = "CurrencyName")
    private String currencyName;

    @JacksonXmlProperty(localName = "ForexBuying")
    private BigDecimal forexBuying;

    @JacksonXmlProperty(localName = "ForexSelling")
    private BigDecimal forexSelling;

    @JacksonXmlProperty(localName = "BanknoteBuying")
    private BigDecimal banknoteBuying;

    @JacksonXmlProperty(localName = "BanknoteSelling")
    private BigDecimal banknoteSelling;

    @JacksonXmlProperty(localName = "CrossRateUSD")
    private String crossRateUSD;

    @JacksonXmlProperty(localName = "CrossRateOther")
    private String crossRateOther;

    @JacksonXmlProperty(localName = "CurrencyCode")
    private String currencyCode;

}