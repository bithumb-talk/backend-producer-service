package com.bithumb.coin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;


@Component
public interface CoinService {
    public String[] getMarket() throws JsonProcessingException;

}
