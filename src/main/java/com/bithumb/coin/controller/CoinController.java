package com.bithumb.coin.controller;

import com.bithumb.coin.service.CoinServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coins")
@RequiredArgsConstructor
public class CoinController {
    private final CoinServiceImpl coinService;
    @GetMapping
    public void getCoins() {
        System.out.println("getCoins call");
        coinService.getQuote();
    }
}
