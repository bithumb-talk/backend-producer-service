package com.bithumb.common.response;

public enum SuccessCode {
	QUOTE_INIT_FINDALL_SUCCESS("FIND ALL QUOTE INIT SUCCESS"),
	RISE_COIN_FINDALL_SUCCESS("FIND ALL RISE COIN LIST SUCCESS"),
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
