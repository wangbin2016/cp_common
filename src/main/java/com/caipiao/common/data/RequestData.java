package com.caipiao.common.data;

import com.caipiao.common.data.body.DataBody;

@lombok.Data
public class RequestData implements Data{
	private String token;
	private DataBody body;
}
