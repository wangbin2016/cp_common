package com.caipiao.common.data.responsestatus;

/**
 * 成功的状态
 * @author wangb
 *
 */
public class CommonResponseStatus extends AbstractResponseStatus{
	public CommonResponseStatus(String code,String text) {
		this.code = code;
		this.text = text;
	}
	public static CommonResponseStatus SUCCESS = new CommonResponseStatus("000000", "OK");
	public static CommonResponseStatus EXCEPTION = new CommonResponseStatus("999999", "请求异常");
}
