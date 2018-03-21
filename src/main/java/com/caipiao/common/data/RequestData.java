package com.caipiao.common.data;

import com.caipiao.common.data.body.DataBody;
import com.caipiao.common.error.ErrorMessage;
@lombok.Data
public class RequestData implements Data {
	private String code;
	private String message;
	private DataBody body;

	public void setError(ErrorMessage error) {
		this.code = error.getErrorCode();
		this.message = error.getErrorMessage();
	}
}
