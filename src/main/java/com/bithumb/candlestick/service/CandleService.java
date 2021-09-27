package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface CandleService {
    public CandleResponse[] getCandleStick(String symbol, String chart_intervals) throws JsonProcessingException;
}
