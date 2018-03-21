package com.caipiao.common.error;

import com.caipiao.common.data.responsestatus.ResponseStatus;

/**
 * 公共异常接口
 * 接收一个返回码
 * @author wangb
 *
 */
public class ServerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	ResponseStatus responseStatus;

	/**
	 * 取服务异常的接代码
	 * 
	 * @return
	 */
	public String getCode() {
		return responseStatus.getStatusCode();
	}

	/**
	 * 取服务异常的信息
	 * 
	 * @return
	 */
	public String getText() {
		return responseStatus.getStatusText();
	}

	public ServerException(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
}
