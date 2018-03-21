package com.caipiao.common.data.responsestatus;

/**
 * 成功的状态
 * @author wangb
 *
 */
public class SuccessResponseStatus extends AbstractResponseStatus{
	public SuccessResponseStatus(String code,String text) {
		this.code = code;
		this.text = text;
	}
	public static SuccessResponseStatus SUCCESS = new SuccessResponseStatus("000000", "OK");

}
