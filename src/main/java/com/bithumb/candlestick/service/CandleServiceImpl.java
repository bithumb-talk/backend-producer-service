package com.bithumb.candlestick.service;

import com.bithumb.candlestick.controller.dto.CandleResponse;
import com.bithumb.candlestick.controller.dto.CandleBithumbResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class CandleServiceImpl implements CandleService {
    @Override
    public CandleResponse[] getCandleStick(String symbol, String chart_intervals) throws JsonProcessingException {
        String jsonInString = "";
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

        String[][] candles = mapper.readValue(jsonInString, CandleBithumbResponse.class).getData();

        CandleResponse[] candleDtos = new CandleResponse[candles.length];

        int i=0;
        for (String[] candle:candles){
            candleDtos[i] = new CandleResponse(candle[0],candle[1],candle[2],candle[3],candle[4],candle[5]);
            i++;
        }
        return candleDtos;
    }


}
