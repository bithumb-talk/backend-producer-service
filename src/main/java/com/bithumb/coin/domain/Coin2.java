package com.bithumb.coin.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Coin2 {
    private String status;
    private String message;
    private CoinDetail[] data;

    @Override
    public String toString() {
        return String.format("%s\n",status);
    }
}
