package com.hrkj.scalp.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: DateUtil
 * @Description:日期转换工具类
 * @author LQR
 * @date 2016-4-25 下午6:49:41
 */
public class DateUtil {
	private static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @Title: parseDateTime 
	 * @Description: 将String 类型转换成Date类型
	 * @param str
	 * @return
	 */
	public static Date parseDateTime(String str) {
		return parseDateTime(str, null);
	}
	
	public static Date parseDateTime(String str, String formatStr) {
		if (StringUtil.isEmpty(formatStr)) {
			if (str.length() == 8) {
				if (str.contains(":")) {
					formatStr = "HH:mm:ss";
				} else {
					formatStr = "yyyyMMdd";
				}
			} else if (str.contains("-")) {
				if (str.contains(":")) {
					formatStr = "yyyy-MM-dd HH:mm:ss";
				} else {
					formatStr = "yyyy-MM-dd";
				}
			} else if (str.contains("/")) {
				if (str.contains(":")) {
					formatStr = "yyyy/MM/dd HH:mm:ss";
				} else {
					formatStr = "yyyy/MM/dd";
				}
			}
		}
		Date data = null;
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
		DateTime dateTime = DateTime.parse(str, dateTimeFormatter);
		data = dateTime.toDate();
		return data;
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param formatStr
	 * @author liu_quan
	 * @return
	 */
	public static String dateToStr(Date date, String formatStr) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
		DateTime dateTime = new DateTime(date);
		String str = dateTime.toString(dateTimeFormatter);
		return str;
	}
	public static String getCurrentDateTime() {
		return dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 将日期格式的字符串转换为数值
	 * @param date
	 * @param formatStr
	 * @author liu_quan
	 * @return
	 */
	public static long dateToLong(String date, String formatStr) {
		if (formatStr == null) {
			formatStr = YYYYMMDDHHMMSS;
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
		DateTime dateTime = DateTime.parse(date, dateTimeFormatter);
		return dateTime.getMillis();
	}
	/**
	 * @Title: getCurrentTime
	 * @Description: 获取当前的系统时间，格式化为yyyy-MM-dd HH:mm:ss
	 * @return String
	 * @throws
	 */
	public static String getCurrentTime() {
		DateTime dateTime = new DateTime();
		return dateTime.toString(YYYYMMDDHHMMSS);
	}

	public static String getMonth() {
		Date dd=new Date();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM");
		String month=sim.format(dd);
		return month;
	}
	/**
	 * @Title: getCurrentTime
	 * @Description:获取当前的系统时间
	 * @param @param format 时间格式(例如：yyyy-MM-dd HH:mm:ss;yyyy-MM-dd;yyyy/MM)
	 * @return String 返回类型
	 * @throws
	 */
	public static String getCurrentTime(String format) {
		DateTime dateTime = new DateTime();
		return dateTime.toString(format);
	}
	/**
	 * @Title: getCurrentDate
	 * @Description: 获取当前的系统时间
	 * @param @throws ParseException 参数说明
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getCurrentDate() {
		DateTime dateTime = new DateTime();
		return new Date(dateTime.getMillis());
	}
	/**
	 * @Title: getCurrentTimeStamp
	 * @Description: 获取当前的系统时间
	 * @param @return 参数说明
	 * @return Timestamp 返回类型
	 * @throws
	 */
	public static Timestamp getCurrentTimeStamp() {
		DateTime dateTime = new DateTime();
		return new Timestamp(dateTime.getMillis());
	}
	/**
	 * @Title: getDateforDiffs
	 * @Description: 获取某个指定时间的偏差天数的日期
	 * @param @param startDateTime 指定开始日期，如没有，，就从1970年1月1日开始算起
	 * @param @param interval 相差天数
	 * @param @param format 时间格式 如： yyyy-MM-dd HH:mm:ss
	 * @param @return
	 * @param @throws ParseExcption 参数说明
	 * @return Date 返回类型
	 * @throws
	 */
	public static String getDateforDiffs(String startDateTime, int interval, String format) throws ParseException {
		DateTime dt = null;
		if (StringUtil.isEmpty(startDateTime)) { // 没用写开始时间的话，就从1970年1月1日开始算起
			dt = new DateTime(interval * 24l * 60l * 60l * 1000l);
		} else {
			DateTimeFormatter jodaformat = DateTimeFormat.forPattern(format);
			DateTime startTime = DateTime.parse(startDateTime, jodaformat);
			dt = new DateTime(startTime.getMillis() + interval * 24l * 60l * 60l * 1000l);
		}
		return dt.toString(format);
	}
	/**
	 * @Title: getFormatTime
	 * @Description: 时间戳转日期格式,格式化为yyyy-MM-dd HH:mm:ss
	 * @param @param times
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFormatTime(Object times) {
		DateTime dateTime = new DateTime(Long.parseLong(String.valueOf(times)));
		return dateTime.toString(YYYYMMDDHHMMSS);
	}
	/**
	 * @Title: getFormatTime
	 * @Description: 时间戳转日期格式
	 * @param @param times
	 * @param @param format 时间格式(例如：yyyy-MM-dd HH:mm:ss;yyyy-MM-dd;yyyy/MM)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFormatTime(Object times, String format) {
		DateTime dateTime = new DateTime(Long.parseLong(String.valueOf(times)));
		return dateTime.toString(format);
	}
	/**
	 * @Title: getFormatTime
	 * @Description: 时间格式的判断
	 * @param timeStr
	 * @return boolean 返回类型
	 */
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) " + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9][.]{0,1}[0-9]{0,3}";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		return matcher.matches();
	}
	public static boolean valiDateTimeFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})(-|/)(0?[1-9]|1[012])(-|/)(0?[1-9]|[12][0-9]|3[01])";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		return matcher.matches();
	}
	/**
	 * @Title: getFormatGMTTime
	 * @Description:转换格林威治时间为普通时间
	 * @param @param timeStr
	 * @param @param format 指定输出格式
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFormatGMTTime(String timeStr, String format) {
		String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(PATTERN);
		DateTime dateTime = dateTimeFormatter.parseDateTime(timeStr);
		return dateTime.toString(format);
	}
}
