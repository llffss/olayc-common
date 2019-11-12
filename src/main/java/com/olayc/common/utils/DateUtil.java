package com.olayc.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类。
 * @author suntao
 * @version V3.0
 * @date 2015-8-12 下午3:36:15
 *
 */
public class DateUtil {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 解析日期。
	 *
	 * @author suntao
	 * @date 2015-8-12 下午3:43:55
	 *
	 * @param dateStr
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String format) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = dateFormat.parse(dateStr);
		return date;
	}

	/**
	 * 格式化日期。
	 *
	 * @author suntao
	 * @date 2015-8-12 下午3:45:14
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		String str = dateFormat.format(date);
		return str;
	}

	public static String formatDate(long date) {
		return formatDate(new Date(date), DEFAULT_FORMAT);
	}

	/**
	 * 格式化现有时间
	 *
	 * @author sky
	 * @date 2016年6月23日 上午11:54:52
	 *
	 * @return
	 */
	public static String formatNow() {
		return formatDate(new Date(), DEFAULT_FORMAT);
	}

	/**
	 * 增加天数。
	 *
	 * @author suntao
	 * @date 2015-8-12 下午3:48:38
	 *
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	/**
	 * 添加年
	 * @param date
	 * @param years
	 * @return
	 */
	public static Date addYear(Date date, int years) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		return calendar.getTime();
	}

	/**
	 * 增加分钟
	 * @author liuyafei
	 * @version V3.0
	 * @date 2015-8-14
	 */
	public static Date addMinute(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	/**
	 * 保留日期的年月日部分，去除时分秒。
	 *
	 * @author Administrator
	 * @date 2015-8-27 下午3:10:52
	 *
	 * @param date
	 * @return
	 */
	public static Date trimToDay(Date date) {
		try {
			return parseDate(formatDate(date, "yyyy-MM-dd"), "yyyy-MM-dd");
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @author sangy
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		long betweenDays = 0L;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			betweenDays = (time2 - time1) / (1000 * 3600 * 24);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 *  字符串的日期格式的计算
	 * @author sangy
	 * @param smdate
	 * @param bdate
	 * @return 相差的天数
	 * @throws ParseException
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(betweenDays));
	}

	/**
	 *
	 * 根据生日计算年龄
	 * @author sangy
	 * @date 2015-12-7 下午5:01:01
	 *
	 * @param birthDay
	 * @return
	 */
	public static Integer getAge(Date birthDay) {
		Integer age = 0;
		try {
			Calendar cal = Calendar.getInstance();
			if (cal.before(birthDay)) {
				return age;
			}
			int yearNow = cal.get(Calendar.YEAR);
			int monthNow = cal.get(Calendar.MONTH) + 1;
			int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

			cal.setTime(birthDay);
			int yearBirth = cal.get(Calendar.YEAR);
			int monthBirth = cal.get(Calendar.MONTH);
			int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

			age = yearNow - yearBirth;
			if (monthNow <= monthBirth) {
				if (monthNow == monthBirth) {
					// monthNow==monthBirth
					if (dayOfMonthNow < dayOfMonthBirth) {
						age--;
					}
				} else {
					// monthNow>monthBirth
					age--;
				}
			}
		} catch (Exception e) {
		}
		return age;
	}

	/**
	 * 计算两个日期中间相差分钟数
	 * @param beginDt
	 * @param endDt
	 * @return
	 */
	public static long computeMin(Date beginDt, Date endDt) {
		long begin = beginDt.getTime();
		long end = endDt.getTime();
		long result = Math.abs(end - begin);
		long s = result / 1000;

		if (s % 60 > 0) {
			result = s / 60 + 1;
		} else {
			result = s / 60;
		}
		return result;
	}

	/**
	 * 将字符日期转换成字符yyyyMMdd格式
	 * @return
	 */
	public static String dateconversion(String date) {
		//日期可能为 XXXX年XX月XX日、XXXX.XX.XX、||有效期格式XXXX.XX.XX-XXXX.XX.XX、XXXX年XX月XX日-XXXX年XX月XX日
		String year = "";
		String month = "";
		String day = "";
		String year1 = "";
		String month1 = "";
		String day1 = "";
		//判断是否为XXXX.XX.XX-XXXX.XX.XX类似的格式
		if (date.indexOf("-") != -1) {
			String[] split = date.split("-");
			if (date.indexOf("年") != -1) {
				//年月日
				String[] splitDay = split[0].split("日");
				String[] splitMonth = splitDay[0].split("月");
				day = splitMonth[1];
				String[] splitYear = splitMonth[0].split("年");
				year = splitYear[0];
				month = splitYear[1];
				String[] splitDay1 = split[1].split("日");
				String[] splitMonth1 = splitDay1[0].split("月");
				day1 = splitMonth1[1];
				String[] splitYear1 = splitMonth1[0].split("年");
				year1 = splitYear1[0];
				month1 = splitYear1[1];
			} else if (date.indexOf(".") != -1) {
				//点的格式
				String[] splitPoint = split[0].split("\\.");
				year = splitPoint[0];
				month = splitPoint[1];
				day = splitPoint[2];
				String[] splitPoint1 = split[1].split("\\.");
				year1 = splitPoint1[0];
				month1 = splitPoint1[1];
				day1 = splitPoint1[2];
			}
			String dateResult = year + month + day + "-" + year1 + month1 + day1;
			return dateResult;
		} else {
			if (date.indexOf("年") != -1) {
				//年月日
				String[] splitDay = date.split("日");
				String[] splitMonth = splitDay[0].split("月");
				day = splitMonth[1];
				String[] splitYear = splitMonth[0].split("年");
				year = splitYear[0];
				month = splitYear[1];
			} else if (date.indexOf(".") != -1) {
				//点的格式
				String[] splitPoint = date.split("\\.");
				year = splitPoint[0];
				month = splitPoint[1];
				day = splitPoint[2];
			}
			String dateResult = year + month + day;
			return dateResult;
		}
	}
}
