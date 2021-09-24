package com.bithumb.rise.service;

import com.bithumb.rise.controller.dto.RiseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RiseServiceImpl implements RiseService {
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOperations;
    @Override
    public RiseDto[] getRise(String intervals) {
        Set<ZSetOperations.TypedTuple<String>> rankSet= zSetOperations.reverseRangeWithScores("rise",0,-1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = rankSet.iterator();
        RiseDto[] riseDtos = new RiseDto[rankSet.size()];
        int i;

        for (i=0;i< rankSet.size();i++) {
            ZSetOperations.TypedTuple<String> values = iterator.next();
            System.out.println("value = "+values.getValue());
            System.out.println("score = "+values.getScore());
            riseDtos[i] = new RiseDto(i+1,values.getValue(), values.getScore());
        }
        return riseDtos;
    }

}
