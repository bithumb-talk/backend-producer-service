package com.bithumb.common.response;

public enum ErrorCode {

	ALREADY_EXISTS("INTEREST ALREADY EXISTS"),
	COIN_NOT_EXISTS("COIN NOT EXISTS"),
	INTEREST_NOT_EXISTS("INTEREST NOT EXISTS");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}