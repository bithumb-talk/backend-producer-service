package com.bithumb.candlestick.controller.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CandleResponse {
    private String baseTime;
    private String openPrice;
    private String closePrice;
    private String highPrice;
    private String lowPrice;
    private String tradeVolume;
}
