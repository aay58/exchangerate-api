package com.project.exchangerateapi.exchangerate.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TarihDate {
    @JacksonXmlProperty(localName = "Currency")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Currency> currencyList;

}