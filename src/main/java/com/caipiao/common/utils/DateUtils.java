package com.caipiao.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DateUtils {
	/**
	 * 日期格式:yy-MM-dd HH:mm
	 */
	public static final String yyMMdd_HHmm="yy-MM-dd HH:mm";
	/**
	 * 日期格式:yy-MM-dd HH:mm:ss
	 */
	public static final String yyMMdd_HHmmss="yy-MM-dd HH:mm:ss";
	/**
	 * 日期格式:yyyy-MM-dd HH:mm:ss
	 */
	public static final String yyyyMMdd_HHmmss="yyyy-MM-dd HH:mm:ss";
	
	public static Date StringToDate(String dateString, String format) {
		Date date;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(dateString);
		} catch (Exception e) {
			log.info("日期格式失败");
			date = null;
		}

		return date;
	}
	
	public static String dateToString(Date date,String format) {
		String dataStr = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dataStr = sdf.format(date);
		} catch (Exception e) {
			log.info("日期格式失败");
		}
		return dataStr;
	}
}
