package com.caipiao.common.data.responsestatus;

/**
 * 会员接口返回状态信息
 * @author wangb
 *
 */
public class MemberResponseStatus extends AbstractResponseStatus{
	public MemberResponseStatus(String code,String text) {
		this.code = code;
		this.text = text;
	}
	public static MemberResponseStatus NOT_LOGIN_STATUS = new MemberResponseStatus("100001", "输入的帐号或者密码错误");
	public static MemberResponseStatus LOGIN_ERROR_STATUS = new MemberResponseStatus("100002", "输入的帐号或者密码错误");
}
