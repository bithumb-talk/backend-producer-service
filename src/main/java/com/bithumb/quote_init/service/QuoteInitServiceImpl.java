package com.bithumb.quote_init.service;

import com.bithumb.candlestick.controller.dto.CandleDto;
import com.bithumb.candlestick.domain.Candle;
import com.bithumb.coin.service.CoinServiceImpl;
import com.bithumb.quote_init.controller.dto.QuoteResponseDetailsDto;
import com.bithumb.quote_init.controller.dto.QuoteResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import java.util.Iterator;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class QuoteInitServiceImpl implements QuoteInitService {
    private final CoinServiceImpl coinService;
    private final RedisTemplate redisTemplate;


    @Override
    public QuoteResponseDetailsDto[] getQuoteInit() {
        String[] markets = coinService.getMarket();

        String jsonInString = "";
        try {
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
            QuoteResponseDto quoteResponseDto = mapper.readValue(jsonInString, QuoteResponseDto.class);
            String obj = mapper.writeValueAsString(quoteResponseDto.getData());
            JSONParser jsonParser = new JSONParser();
            JSONObject resultObj = (JSONObject)jsonParser.parse(obj);

            HashOperations operation = redisTemplate.opsForHash();

            int i;
            //date 제거
            QuoteResponseDetailsDto[] quotes = new QuoteResponseDetailsDto[resultObj.size()-1];
            Iterator keyIterator = resultObj.keySet().iterator();
            Iterator valuesIterator = resultObj.values().iterator();
            for (i=0;i<resultObj.size()-1;i++) {
                quotes[i] = new QuoteResponseDetailsDto();
                String symbol = keyIterator.next().toString();
                String values = valuesIterator.next().toString();
                quotes[i].setSymbol(symbol);
                if (symbol.equals("date")) {
                    symbol = keyIterator.next().toString();
                    values = valuesIterator.next().toString();
                }
                String korean = new String((byte[]) operation.get(symbol+"_KRW",symbol+"_KRW"),"UTF-8");
                quotes[i].setKorean(korean);
                JSONObject resultObj1 = (JSONObject)jsonParser.parse(values);
                quotes[i].setClosePrice(resultObj1.get("closing_price").toString());
                quotes[i].setChgAmt(resultObj1.get("fluctate_24H").toString());
                quotes[i].setChgRate(resultObj1.get("fluctate_rate_24H").toString());
                quotes[i].setTradeVolume(resultObj1.get("units_traded_24H").toString());
            }
            return quotes;
        }catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
