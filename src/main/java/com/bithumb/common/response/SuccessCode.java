package com.bithumb.common.response;

public enum SuccessCode {

	COIN_FINDALL_SUCCESS("FIND ALL COIN LIST SUCCESS"),
	CANDLESTICK_FINDALL_SUCCESS("FIND GET CANDLE LIST SUCCESS");

	private final String message;

	SuccessCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
