package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleDto;
import com.bithumb.candlestick.domain.Candle;
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
public class CandleServiceImpl implements CandleService {
    private final RedisTemplate redisTemplate;
    @Override
    public CandleDto[] getCandleStick(String symbol, String chart_intervals) {
        String jsonInString = "";
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setConnectTimeout(5000);
            factory.setReadTimeout(5000);
            RestTemplate restTemplate = new RestTemplate(factory);

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://api.bithumb.com/public/candlestick/"+symbol+"_KRW/"+chart_intervals;
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity, Map.class);
            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap.getBody());
            String[][] candles = mapper.readValue(jsonInString, Candle.class).getData();

            CandleDto[] candleDtos = new CandleDto[candles.length];
            int i=0;
            for (String[] candle:candles){
                candleDtos[i] = new CandleDto(candle[0],candle[1],candle[2],candle[3],candle[4],candle[5]);
                i++;
            }
            return candleDtos;

        }catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
