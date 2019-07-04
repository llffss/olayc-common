package com.olayc.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入。
 *
 * @author sangy
 * @version V3.0
 */
@SuppressWarnings("AlibabaCommentsMustBeJavadocFormat")
public class ArithUtil {
	/**
	 * 默认除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 10;

	private ArithUtil() {
	}

	/**
	 * 提供精确的加法运算。
	 *
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 *
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 *
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 *
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 *
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * @param yuan
	 *            需要转换的元单位数字
	 * @return 将元*100 转换成分的字符串
	 * 默认对分后的单位做四舍五入运算  如  4.5555 转换为  456
	 */
	public static String yuan2fen(double yuan) {
		DecimalFormat decimalFormat = new DecimalFormat("0");
		return decimalFormat.format(yuan * 100);
	}

/*	*//**
	 * 格式金融单位元
	 * 0.00元
	 * @param yuan
	 * @return
	 *//*
	public static String formatYuan(Double yuan) {
		DecimalFormat DECIMALFORMAT_YUAN = new DecimalFormat("0.00");
		return DECIMALFORMAT_YUAN.format(round(yuan, 2));

	}*/

	/**
	 * 计算预约时长。 不足一分钟安一分钟计算
	 * sangy
	 * @param
	 * @param
	 * @return 计算租车开始的时间 和租车结束的时间中的分钟间隔
	 * 如果没有结束时间认为 认为时间间隔为 0
	 */
	public static Integer calRentMinutes(Date beginDate, Date endDate) {
		int minutes = 0;
		Long timeLong = endDate.getTime() - beginDate.getTime();
		Integer timeUnit = 1000 * 60;
		Double dMinutes = Math.ceil(div(timeLong, timeUnit));
		minutes = dMinutes.intValue();
		if (minutes < 1) {
			minutes = 1;
		}
		return minutes;
	}

	/**
	 *
	 * 返回两个时间差的分钟单位相差的时间间隔
	 * @author sangy
	 * @date 2015-11-18 下午5:12:54
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Integer calAbsMinutes(Date beginDate, Date endDate) {
		Double beginMinute = div(beginDate.getTime(), (60 * 1000));
		Double endMinute = div(endDate.getTime(), (60 * 1000));
		Double z = sub(endMinute, beginMinute);
		int minutes = z.intValue();
		if (minutes == 0) {
			minutes = 1;
		}
		return minutes;
	}

	/**
	 *
	 * @date 2015-9-14 上午10:39:54
	 *
	 * @param d
	 * @return
	 */
	public static Double absoluteValue(Double d) {
		BigDecimal b1 = new BigDecimal(Double.toString(d));
		return b1.abs().doubleValue();
	}

	/**
	 * 按照给定的变量变更操作 
	 * 正数  做 加的操作
	 * 负数 做 减法操作 
	 * @date 2015-9-14 上午10:44:57
	 *
	 * @param sourceVal  基数
	 * @param variableVal 变更量
	 * @return
	 */
	public static Double getValChange(Double sourceVal, Double variableVal) {
		if (variableVal < 0) {
			return sub(sourceVal, absoluteValue(variableVal));
		} else {
			return add(sourceVal, variableVal);
		}
	}
}
