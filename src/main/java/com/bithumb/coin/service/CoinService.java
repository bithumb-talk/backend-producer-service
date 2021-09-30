package com.bithumb.coin.service;


import com.bithumb.coin.domain.Coin;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public interface CoinService {
    public HashMap<String, Coin> getCoins() throws IOException;
}