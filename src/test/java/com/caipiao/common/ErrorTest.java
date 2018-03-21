package com.caipiao.common;

import com.caipiao.common.data.responsestatus.MemberResponseStatus;
import com.caipiao.common.error.ServerException;

public class ErrorTest {

	public static void main(String[] args) {
		try {
			throw new ServerException(MemberResponseStatus.LOGIN_ERROR_STATUS);
		} catch (Exception e) {
			if(e instanceof ServerException) {
				String code = ((ServerException)e).getCode();
				System.out.println(code);
			}
		}
	}
}
