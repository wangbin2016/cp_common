package com.caipiao.common.data;

import com.caipiao.common.data.request.RequestBody;

@lombok.Data
public class RequestData implements Data{
	private String token;
	private RequestBody body;
}
