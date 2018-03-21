package com.caipiao.common.data.body.member;

import com.caipiao.common.data.body.DataBody;

import lombok.Data;

@Data
public class LoginBody implements DataBody {
	private String account;
	private String password;
}

@Data
class LoginBodyResponse implements DataBody {
	private String account;
	private String password;
}