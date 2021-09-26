package com.bithumb.candlestick.controller;

import com.bithumb.candlestick.service.CandleServiceImpl;
import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/candlestick")
@RequiredArgsConstructor
public class CandleController {
    private final CandleServiceImpl candleService;

    @GetMapping("{symbol}/{chart_intervals}")
    public ResponseEntity<?> getCandleStick(@PathVariable(value="symbol") String symbol, @PathVariable(value = "chart_intervals")String chart_intervals) throws JsonProcessingException {

        ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
                SuccessCode.CANDLESTICK_FINDALL_SUCCESS.getMessage());
        apiResponse.setData(candleService.getCandleStick(symbol, chart_intervals));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
