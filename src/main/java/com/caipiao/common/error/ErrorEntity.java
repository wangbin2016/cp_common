package com.caipiao.common.error;

public class ErrorEntity implements ErrorMessage {

	protected String errorCode;
	protected String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public ErrorEntity(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
