package com.caipiao.common;

import com.caipiao.common.error.MemberError;
import com.caipiao.common.error.ServerException;

public class ErrorTest {

	public static void main(String[] args) {
		try {
			throw new ServerException(MemberError.LOGIN_ERROR);
		} catch (Exception e) {
			if(e instanceof ServerException) {
				String code = ((ServerException)e).getErrorCode();
				System.out.println(code);
			}
		}
	}
}
