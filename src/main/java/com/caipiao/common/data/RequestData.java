package com.caipiao.common.data;

import com.caipiao.common.data.body.DataBody;

/**
 * 请求接口实体  目前未使用
 * @author wangb
 *
 * @param <T>
 */
@lombok.Data
public class RequestData<T extends DataBody>  {
	/**
	 * 请求的token
	 */
	private String token;
	/**
	 * 请求的实体
	 */
	private T body;
}
