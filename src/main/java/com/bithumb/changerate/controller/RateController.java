package com.bithumb.changerate.controller;

import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import com.bithumb.changerate.service.RateServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/rise")
@RequiredArgsConstructor
public class RateController {
    private final RateServiceImpl riseService;

    @GetMapping("{intervals}")
    public ResponseEntity<?> getRiseCoin(@PathVariable(value = "intervals") String intervals) {

        ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
                SuccessCode.RISE_COIN_FINDALL_SUCCESS.getMessage());
        apiResponse.setData(riseService.getRise(intervals));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}