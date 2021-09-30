//package com.bithumb.changerate.service;
//
//import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
//import com.bithumb.quoteinit.controller.dto.QuoteInitBithumbResponse;
//import lombok.Data;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//
//import javax.annotation.Resource;
//import java.io.Serializable;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.times;
//
//class RateServiceImplTest {
//    @InjectMocks
//    RateServiceImpl rateService;
//
//    @Mock
//    RedisTemplate redisTemplate;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Data
//    public class RangeVo implements Serializable {
//
//        private String symbol;
//
//        private Double price;
//
//    }
//    @Test
//    void getSortChangeRate() {
//        for (int i = 0; i< 10; i++) {
//            int score = new Random().nextInt(50);
//            redisTemplate.opsForZSet().add("changerate","비트코인"+i,score);
//        }
//        List<RangeVo> list = new ArrayList<>();
//        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeByScoreWithScores("changerate",0,-1);
//        Iterator<ZSetOperations.TypedTuple<Object>> iterator = set.iterator();
//        while (iterator.hasNext()){
//            ZSetOperations.TypedTuple<Object> next = iterator.next();
//            RangeVo rangeVo = new RangeVo();
//            rangeVo.setSymbol((String) next.getValue());
//            rangeVo.setPrice(next.getScore());
//            list.add(rangeVo);
//        }
//        System.out.println(list);
//    }
//}