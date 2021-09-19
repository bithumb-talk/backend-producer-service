package com.bithumb.websocket.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private String korean;
    private String tickType;
    private String date;
    private String time;
    private String openPrice;
    private String closePrice;
    private String lowPrice;
    private String highPrice;
    private String value;
    private String volume;
    private String sellVolume;
    private String buyVolume;
    private String prevClosePrice;
    private String chgRate;
    private String chgAmt;
    private String volumePower;
    private String symbol;
}
