package com.bithumb.coin.service;

import com.bithumb.coin.domain.Coin;
import com.bithumb.coin.domain.CoinDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CoinServiceImpl implements CoinService {
    private final RedisTemplate redisTemplate;
    @Override
    public String[] getQuote() {
        String jsonInString = "";
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "http://172.27.0.1:8080/coins";
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity, Map.class);
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap.getBody());

            Coin coin = mapper.readValue(jsonInString, Coin.class);
            HashOperations operation = redisTemplate.opsForHash();
            int i;
            CoinDetail[] coins = coin.getData();
            String[] str = new String[coins.length];
            for (i=0; i<coins.length;i++){
                str[i] = coins[i].getMarket();

                operation.put(coins[i].getMarket(),coins[i].getMarket(),coins[i].getKorean().getBytes(StandardCharsets.UTF_8));
            }
            return str;

        }catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
