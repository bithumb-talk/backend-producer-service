package com.bithumb.quoteinit.controller.dto;

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
public class QuoteInitResponse {
    private String symbol;
    private String korean;
    private String closePrice;
    private String chgRate;
    private String chgAmt;
    private String TradeValue;
}
