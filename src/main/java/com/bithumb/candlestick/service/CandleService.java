package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleDto;
import org.springframework.stereotype.Component;

@Component
public interface CandleService {
    public CandleDto[] getCandleStick(String symbol, String chart_intervals);
}
