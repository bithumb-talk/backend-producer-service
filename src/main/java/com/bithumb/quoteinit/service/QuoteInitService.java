package com.bithumb.quoteinit.service;

import com.bithumb.quoteinit.controller.dto.QuoteInitResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public interface QuoteInitService {
    public QuoteInitResponse[] getQuoteInit() throws IOException, ParseException;
}
