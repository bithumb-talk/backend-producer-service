package com.bithumb.rise.controller;

import com.bithumb.common.response.ApiResponse;
import com.bithumb.common.response.StatusCode;
import com.bithumb.common.response.SuccessCode;
import com.bithumb.rise.service.RiseServiceImpl;
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
public class RiseController {
    private final RiseServiceImpl riseService;

    @GetMapping("{intervals}")
    public ResponseEntity<?> getCandleStick(@PathVariable(value = "intervals") String intervals) {

        ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
                SuccessCode.RISE_COIN_FINDALL_SUCCESS.getMessage());
        apiResponse.setData(riseService.getRise(intervals));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
