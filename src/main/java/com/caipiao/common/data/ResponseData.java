package com.caipiao.common.data;

import com.caipiao.common.data.body.DataBody;
import com.caipiao.common.data.responsestatus.ResponseStatus;

/**
 * 响应接口的实体
 * 
 * @author wangb
 *
 * @param <T>
 */
@lombok.Data
public class ResponseData<T extends DataBody> {
	private String code;
	private String text;
	private T body;

	public ResponseData() {

	}
	
	public ResponseData(ResponseStatus status) {
		this.code = status.getStatusCode();
		this.text = status.getStatusText();
	}

	public ResponseData(ResponseStatus status, T t) {
		this.code = status.getStatusCode();
		this.text = status.getStatusText();
		body = t;
	}

	public void setResponseStatus(ResponseStatus status) {
		this.code = status.getStatusCode();
		this.text = status.getStatusText();
	}
}
