package com.caipiao.common.data.responsestatus;

/**
 * 
 * @author wangb
 *
 */
public abstract class AbstractResponseStatus implements ResponseStatus {
	protected String code;
	protected String text;
	
	public AbstractResponseStatus(ResponseStatus status) {
		this.code = status.getStatusCode();
		this.text = status.getStatusText();
	}
	
	public AbstractResponseStatus() {}
	
	@Override
	public String getStatusText() {
		return text;
	}

	@Override
	public String getStatusCode() {
		return code;
	}

}
