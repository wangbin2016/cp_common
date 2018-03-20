package com.caipiao.common.error;

public class MemberError extends ErrorEntity{

	public MemberError(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public static MemberError LOGIN_ERROR = new MemberError("100001", "输入的帐号或者密码错误");

}
