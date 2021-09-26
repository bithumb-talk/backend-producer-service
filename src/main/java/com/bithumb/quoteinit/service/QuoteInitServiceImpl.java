package com.bithumb.quoteinit.service;

import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.quoteinit.controller.dto.QuoteInitResponse;
import com.bithumb.quoteinit.controller.dto.QuoteInitBithumbResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuoteInitServiceImpl implements QuoteInitService {
    private final CoinServiceImpl coinService;
    private final RedisTemplate redisTemplate;


    @Override
    public QuoteInitResponse[] getQuoteInit() throws JsonProcessingException, UnsupportedEncodingException, ParseException {
        String[] markets = coinService.getMarket();

        String jsonInString = "";
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders header = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(header);

        String url = "https://api.bithumb.com/public/ticker/ALL_KRW";
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

        ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity, Map.class);
        ObjectMapper mapper = new ObjectMapper();
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap.getBody());


        QuoteInitBithumbResponse quoteResponseDto = mapper.readValue(jsonInString, QuoteInitBithumbResponse.class);
        String obj = mapper.writeValueAsString(quoteResponseDto.getData());
        JSONParser jsonParser = new JSONParser();
        JSONObject resultObj = (JSONObject)jsonParser.parse(obj);

        HashOperations operation = redisTemplate.opsForHash();

        int i;
            //date 제거
        QuoteInitResponse[] quotes = new QuoteInitResponse[resultObj.size()-1];
        Iterator keyIterator = resultObj.keySet().iterator();
        Iterator valuesIterator = resultObj.values().iterator();
        for (i=0;i<resultObj.size()-1;i++) {
            quotes[i] = new QuoteInitResponse();
            String symbol = keyIterator.next().toString();
            String values = valuesIterator.next().toString();
            quotes[i].setSymbol(symbol);
            if (symbol.equals("date")) {
                symbol = keyIterator.next().toString();
                values = valuesIterator.next().toString();
            }

            //redis -> S3로 변경 해야 함.
            String korean = new String((byte[]) operation.get(symbol+"_KRW",symbol+"_KRW"),"UTF-8");
            quotes[i].setKorean(korean);
            JSONObject resultObj1 = (JSONObject)jsonParser.parse(values);
            quotes[i].setClosePrice(resultObj1.get("closing_price").toString());
            quotes[i].setChgAmt(resultObj1.get("fluctate_24H").toString());
            quotes[i].setChgRate(resultObj1.get("fluctate_rate_24H").toString());
            quotes[i].setTradeValue(resultObj1.get("acc_trade_value_24H").toString());
        }
        return quotes;
    }
}
