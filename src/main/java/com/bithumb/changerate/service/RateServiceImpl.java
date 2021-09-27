package com.bithumb.changerate.service;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class RateServiceImpl implements RateService {
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zSetOperations;
    @Override
    public SortChangedRateResponse[] getSortChangeRate() {
        Set<ZSetOperations.TypedTuple<String>> rankSet= zSetOperations.reverseRangeWithScores("changerate",0,-1);
        Iterator<ZSetOperations.TypedTuple<String>> iterator = rankSet.iterator();
        SortChangedRateResponse[] riseDtos = new SortChangedRateResponse[rankSet.size()];
        int i;

        for (i=0;i< rankSet.size();i++) {
            ZSetOperations.TypedTuple<String> values = iterator.next();
            System.out.println("value = "+values.getValue());
            System.out.println("score = "+values.getScore());
            riseDtos[i] = new SortChangedRateResponse(i+1,values.getValue(), values.getScore());
        }
        return riseDtos;
    }

}
