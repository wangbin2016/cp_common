package com.caipiao.common.error;

/**
 * 公共异常接口
 * @author wangb
 *
 */
public class ServerException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Error errorEntity;
	
	/**
	 * 取服务异常的接代码
	 * @return
	 */
	public String getErrorCode() {
		return errorEntity.getErrorCode();
	}
	
	/**
	 * 取服务异常的信息
	 * @return
	 */
	public String getErrorMessage() {
		return errorEntity.getErrorMessage();
	}
	
	public ServerException(Error errorEntity) {
		this.errorEntity = errorEntity;
	}
}
