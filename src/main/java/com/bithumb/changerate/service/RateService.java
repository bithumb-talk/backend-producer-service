package com.bithumb.changerate.service;

import com.bithumb.changerate.controller.dto.SortChangedRateResponse;
import org.springframework.stereotype.Component;

@Component
public interface RateService {
    public SortChangedRateResponse[] getSortChangeRate();
}
