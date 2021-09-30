package com.bithumb.quoteinit.controller;

import com.bithumb.quoteinit.service.QuoteInitServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/quote_init")
@RequiredArgsConstructor
public class QuoteInitController {
    private final QuoteInitServiceImpl quoteInitService;

    @GetMapping
    public ResponseEntity<?> getQuoteInit() throws IOException, ParseException {

        ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
                SuccessCode.QUOTE_INIT_FINDALL_SUCCESS.getMessage());
        apiResponse.setData(quoteInitService.getQuoteInit());
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
