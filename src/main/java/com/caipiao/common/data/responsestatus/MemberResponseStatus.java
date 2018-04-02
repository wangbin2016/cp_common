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
	public static MemberResponseStatus NOT_LOGIN = new MemberResponseStatus("100001", "用户未登录");
	public static MemberResponseStatus LOGIN_ERROR = new MemberResponseStatus("100002", "帐号或者密码错误");
	public static MemberResponseStatus LOGIN_INFO_ERROR = new MemberResponseStatus("100003", "用户登录信息有误");
	public static MemberResponseStatus MEMBER_LOCK_ERROR = new MemberResponseStatus("100004", "帐号被锁定,请联系客户");
}
