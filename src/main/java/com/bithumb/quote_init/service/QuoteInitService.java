package com.bithumb.quote_init.service;

import com.bithumb.quote_init.controller.dto.QuoteResponseDetailsDto;
import com.bithumb.quote_init.controller.dto.QuoteResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface QuoteInitService {
    public QuoteResponseDetailsDto[] getQuoteInit();
}
