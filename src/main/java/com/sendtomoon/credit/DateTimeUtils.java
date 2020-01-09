package com.sendtomoon.credit;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;

public final class DateTimeUtils {

	private DateTimeUtils() {
	}

	// 默认日期时间格式（包含毫秒）
	public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	// 默认日期时间格式
	public static final String DEFAULT_DATE_TIME_NOMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式
	 */
	public static final String PATTERN_DATE = "yyyyMMdd";

	/**
	 * 日期格式
	 */
	public static final String PATTERN_DATE2 = "yyyy-MM-dd";

	/**
	 * 时间格式
	 */
	public static final String PATTERN_TIME = "HHmmss";

	public static final String PATTERN_DATE_TIME = "yyyyMMddHHmmss";

	// 简单日期时间格式
	public static final String SIMPLE_DATE_PATTERN = "yyMMdd";

	public static final String PATTERN_DATE3 = "yyyy-MM-dd HH:mm";

	/**
	 * 时间间隔(ms)
	 * 
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return
	 */
	public static long timeInterval(Date start, Date end) {
		return new Interval(new DateTime(start), new DateTime(end)).toDurationMillis();
	}

	/**
	 * 时间间隔(days),忽略时分秒
	 * 
	 * @param start 开始时间
	 * @param end   结束时间
	 * @return
	 */
	public static int daysInterval(Date start, Date end) {
		return new Interval(new DateTime(indentDate(start)), new DateTime(indentDate(end))).toPeriod(PeriodType.days())
				.getDays();
	}

	/**
	 * 以特定的格式格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return new DateTime(date).toString(pattern);
	}

	/**
	 * 以格式"yyyy-MM-dd HH:mm:ss SSS"格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return new DateTime(date).toString(DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * 以特定的格式格式化当前时间
	 * 
	 * @param pattern
	 * @return
	 */
	public static String formatNow(String pattern) {
		return formatDate(DateTime.now().toDate(), pattern);
	}

	/**
	 * 以格式"yyyy-MM-dd HH:mm:ss SSS"格式化当前时间
	 * 
	 * @return
	 */
	public static String formatNow() {
		return formatDate(DateTime.now().toDate(), DEFAULT_DATE_TIME_PATTERN);
	}

	/**
	 * 解析特定格式的日期字符串
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		// 如下JODA自带的方法无法解析1986-05-04，存在缺陷
		// return DateTime.parse(dateStr, DateTimeFormat.forPattern(pattern)).toDate();
		return DateHelper.str2Date(dateStr, pattern);
	}

	/**
	 * 比较日期大小
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return 返回0，则比较值==基准值；返回-1，则比较值<基准值；返回1，则比较值>基准值
	 */
	public static int compareTo(String comparisonValue, String fiducialValue, String pattern) {
		DateTime comparisonDate = DateTime.parse(comparisonValue, DateTimeFormat.forPattern(pattern));
		DateTime fiducialDate = DateTime.parse(fiducialValue, DateTimeFormat.forPattern(pattern));
		return comparisonDate.compareTo(fiducialDate);
	}

	/**
	 * 是否比较值小于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isLessThan(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) == -1;
	}

	/**
	 * 是否比较值小于等于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isLessEqual(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) <= 0;
	}

	/**
	 * 是否比较值等于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isEqual(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) == 0;
	}

	/**
	 * 是否比较值不等于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isNotEqual(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) != 0;
	}

	/**
	 * 是否比较值大于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isGreaterThan(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) == 1;
	}

	/**
	 * 是否比较值大于等于基准值
	 * 
	 * @param comparisonValue 比较值
	 * @param fiducialValue   基准值
	 * @param pattern         日期格式样式
	 * @return
	 */
	public static boolean isGreaterEqual(String comparisonValue, String fiducialValue, String pattern) {
		return compareTo(comparisonValue, fiducialValue, pattern) >= 0;
	}

	/**
	 * 缩进毫秒
	 * 
	 * @param date 处理前时间
	 * @return 2014:10:11 23:12:20 123 -> 2014:10:11 23:12:20 000
	 */
	public static Date indentMillisecond(Date date) {
		return new DateTime(date).millisOfSecond().withMinimumValue().toDate();
	}

	/**
	 * 缩进日期
	 * 
	 * @param date 处理前时间
	 * @return 2014:10:11 23:12:20 -> 2014:10:11 00:00:00
	 */
	public static Date indentDate(Date date) {
		return new DateTime(date).millisOfDay().withMinimumValue().toDate();
	}

	/**
	 * 根据出生日期计算年龄(周岁)
	 * 
	 * @param birthday
	 * @return
	 */
	public static int calcAge(Date birthday) {
		if (birthday == null) {
			return 0;
		}
		Calendar cal = new GregorianCalendar();
		int yearNow = cal.get(Calendar.YEAR);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(birthday);
		int yearBirth = cal2.get(Calendar.YEAR);

		// 当前年份减去出生年份，即两个年份之间的差值
		int age = yearNow - yearBirth;
		cal2.add(Calendar.YEAR, age);
		if (cal2.after(cal)) {
			// 出生日期加上相差的年份大于当前日期，实际年龄为年份差值-1
			return age - 1;
		} else {
			return age;
		}
	}

	/**
	 * 根据身份证号计算年龄(周岁)
	 * 
	 * @author CHENZEFENG995
	 * @param idNo 身份证号，这里未对身份证号的合法性进行校验
	 * @return 如果未能从身份证号上取到有效的日期，返回0
	 */
	public static int calcAge(String idNo) {
		Date birthday = getBirthday(idNo);
		return calcAge(birthday);
	}

	/**
	 * 根据身份证号取出生日期。
	 * 
	 * @author CHENZEFENG995
	 * @param idNo 身份证号，这里未对身份证号的合法性进行校验
	 * @return 如果为15位身份证号，取7-12位；如果为18位身份证号，取7-14位。对取到的数据转化成Date类型并返回。
	 *         如果为其他情况，返回null
	 */
	public static Date getBirthday(String idNo) {
		if (StringUtils.isNotBlank(idNo)) {
			if (idNo.length() == 18) {
				String str = idNo.substring(6, 14);
				return parseDate(str, PATTERN_DATE);
			} else if (idNo.length() == 15) {
				String str = idNo.substring(6, 12);
				return parseDate(str, SIMPLE_DATE_PATTERN);
			}
		}
		return null;
	}

	/**
	 * 根据身份证号取出生日期(yyyyMMdd格式)。
	 * 
	 * @author CHENZEFENG995
	 * @param idNo 身份证号，这里未对身份证号的合法性进行校验
	 * @return 先获取身份证号中的出生日期数据并解析成Date类型，最后格式化为yyyyMMdd格式的字符串。不能解析成Date类型时返回空字符串
	 */
	public static String getBirthdayStr(String idNo) {
		Date birthday = getBirthday(idNo);
		if (birthday == null) {
			return "";
		}
		return formatDate(birthday, PATTERN_DATE);
	}

	/**
	 * 根据身份证号取出生日期并指定格式
	 * 
	 * @author CHENZEFENG995
	 * @param idNo    身份证号，这里未对身份证号的合法性进行校验
	 * @param pattern 指定的日期格式
	 * @return 先获取身份证号中的出生日期数据并解析成Date类型，最后格式化为指定格式的字符串。不能解析成Date类型时返回空字符串
	 */
	public static String getBirthdayStr(String idNo, String pattern) {
		Date birthday = getBirthday(idNo);
		if (birthday == null) {
			return "";
		}
		return formatDate(birthday, pattern);
	}

	/**
	 * 根据身份证号取性别
	 * 
	 * @param idNo
	 * @return 如果不为15位或18位身份证号，返回空字符串，否则取倒数第二位的数字，为奇数时返回0，偶数返回1。
	 */
	public static String getSex(String idNo) {
		if (StringUtils.isNotBlank(idNo)) {
			int length = idNo.length();
			if (length == 15 || length == 18) {
				String sex = idNo.substring(length - 2, length - 1);
				int i = Integer.parseInt(sex) % 2;
				return i == 1 ? "0" : "1";
			}
		}
		return "";
	}

}
