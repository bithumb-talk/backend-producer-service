package com.bithumb.rise.service;

import com.bithumb.rise.controller.dto.RiseDto;
import org.springframework.stereotype.Component;

@Component
public interface RiseService {
    public RiseDto[] getRise(String intervals);
}
