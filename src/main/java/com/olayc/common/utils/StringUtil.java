package com.olayc.common.utils;

import java.util.*;

/**
 * 字符串工具类。
 *
 * @author suntao
 * @version V3.0
 * @date 2015-9-2 下午4:38:38
 *
 */
public class StringUtil {
	private static final String ALPHA_NUM = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String ALPHA_NUMT = "0123456789";

	/**
	 * 判断字符串是否为空。
	 *
	 * @author suntao
	 * @date 2015-9-2 下午4:39:44
	 *
	 * @param str
	 *            待判断的字符串。
	 * @return 字符串为null或长度为0时返回true，否则返回false。
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 判断给定对象不为空
	 *
	 * @author sangy
	 * @date 2015-10-16 下午2:40:05
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		} else {
			return !isEmpty(String.valueOf(obj));
		}
	}

	/**
	 * 判断给定对象不为空
	 *
	 * @author sangy
	 * @date 2015-10-16 下午2:40:05
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj, boolean b) {
		if (obj == null) {
			return false;
		} else {
			return !isEmpty(String.valueOf(obj), b);
		}
	}

	/**
	 * 判断字符串是否为空。
	 *
	 * @author suntao
	 * @date 2015-9-2 下午4:41:44
	 *
	 * @param str
	 *            待判断的字符串。
	 * @param afterTrim
	 *            是否trim后判断。
	 * @return 字符串为null或长度为0时（如afterTrim为true，则trim后判断）返回true，否则返回false。
	 */
	public static boolean isEmpty(String str, boolean afterTrim) {
		if (str == null) {
			return true;
		}

		if (afterTrim) {
			str = str.trim();
		}
		return str.length() == 0;
	}

	/**
	 * 左填充。
	 *
	 * @author suntao
	 * @date 2015-9-2 下午4:51:13
	 *
	 * @param str
	 *            待填充的字符串。
	 * @param totalLen
	 *            填充后的总长度。
	 * @param padChar
	 *            填充的字符。
	 * @param fixTotalLen
	 *            是否固定长度，如果为true并且str长度超过totalLen，则截取右侧totalLen位字符。
	 * @return 填充后的字符串。
	 */
	public static String lpad(String str, int totalLen, char padChar, boolean fixTotalLen) {
		if (str != null && str.length() == totalLen) {
			return str;
		} else if (str != null && str.length() > totalLen) {
			if (fixTotalLen) {
				return str.substring(str.length() - totalLen);
			} else {
				return str;
			}
		}

		int len = (str == null) ? 0 : str.length();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < totalLen - len; i++) {
			builder.append(padChar);
		}
		builder.append(str);

		return builder.toString();
	}

	/**
	 * 根据会员membercardId 生成会员的八位十六进制 字符串
	 *
	 * @author sangy
	 * @date 2016-1-8 下午3:15:55
	 *
	 * @param memberCardId
	 * @return
	 */
	public static String chang2Hx(Long memberCardId) {
		if (memberCardId == null) {
			return "";
		}
		String hxResult = Long.toHexString(memberCardId).toUpperCase();
		StringBuffer sbff = new StringBuffer();
		if (hxResult.length() > 8) {
			hxResult = hxResult.substring(hxResult.length() - 8, hxResult.length());
		}
		for (int i = hxResult.length(); i < 8; i++) {
			sbff.append("0");
		}
		sbff.append(hxResult);
		return sbff.toString();
	}

	public static String chang2Hx(Integer memberCardId) {
		if (memberCardId == null) {
			return "";
		}
		String hxResult = Integer.toHexString(memberCardId).toUpperCase();
		StringBuffer sbff = new StringBuffer();
		if (hxResult.length() > 8) {
			hxResult = hxResult.substring(hxResult.length() - 8, hxResult.length());
		}
		for (int i = hxResult.length(); i < 8; i++) {
			sbff.append("0");
		}
		sbff.append(hxResult);
		return sbff.toString();
	}

	public static String randomAlphaNum(int len) {
		if (len <= 0) {
			return "";
		}

		Random random = new Random();
		int charLen = ALPHA_NUM.length();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			builder.append(ALPHA_NUM.charAt(random.nextInt(charLen)));
		}

		return builder.toString();
	}

	public static String randomAlphaAllNum(int len) {
		if (len <= 0) {
			return "";
		}

		Random random = new Random();
		int charLen = ALPHA_NUMT.length();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < len; i++) {
			builder.append(ALPHA_NUMT.charAt(random.nextInt(charLen)));
		}

		return builder.toString();
	}

	/**
	 * 自定义的分隔字符串函数 例如: 1,2,3 =>[1,2,3] 3个元素 ,2,3=>[,2,3] 3个元素 ,2,3,=>[,2,3,] 4个元素 ,,,=>[,,,] 4个元素
	 *
	 * 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素
	 *
	 * @param split
	 *            分割字符 默认,
	 * @param src
	 *            输入字符串
	 * @return 分隔后的list
	 */
	public static List<String> splitToList(String split, String src) {
		// 默认,
		String sp = ",";
		if (split != null && split.length() == 1) {
			sp = split;
		}
		List<String> r = new ArrayList<String>();
		int lastIndex = -1;
		int index = src.indexOf(sp);
		if (-1 == index && src != null) {
			r.add(src);
			return r;
		}
		while (index >= 0) {
			if (index > lastIndex) {
				r.add(src.substring(lastIndex + 1, index));
			} else {
				r.add("");
			}

			lastIndex = index;
			index = src.indexOf(sp, index + 1);
			if (index == -1) {
				r.add(src.substring(lastIndex + 1, src.length()));
			}
		}
		return r;
	}

	/**
	 * 转换为下划线
	 *
	 * @param camelCaseName
	 * @return
	 */
	public static String underscoreName(String camelCaseName) {
		StringBuilder result = new StringBuilder();
		if (camelCaseName != null && camelCaseName.length() > 0) {
			result.append(camelCaseName.substring(0, 1).toLowerCase());
			for (int i = 1; i < camelCaseName.length(); i++) {
				char ch = camelCaseName.charAt(i);
				if (Character.isUpperCase(ch)) {
					result.append("_");
					result.append(Character.toLowerCase(ch));
				} else {
					result.append(ch);
				}
			}
		}
		return result.toString();
	}

	/**
	 * 转换为驼峰
	 *
	 * @param underscoreName
	 * @return
	 */
	public static String camelCaseName(String underscoreName) {
		StringBuilder result = new StringBuilder();
		if (underscoreName != null && underscoreName.length() > 0) {
			boolean flag = false;
			for (int i = 0; i < underscoreName.length(); i++) {
				char ch = underscoreName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				} else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					} else {
						result.append(ch);
					}
				}
			}
		}
		return result.toString();
	}

	public static List<String> splitTwoParts(String split, String src) {
		// 默认,
		String splitDefault = ",";
		String realSplit = splitDefault;
		if (split != null && split.length() == 1) {
			realSplit = split;
		}
		int index = src.indexOf(realSplit);
		if (-1 == index && src != null) {
			return Collections.singletonList(src);
		}
		return Arrays.asList(src.substring(0, index), src.substring(index + 1, src.length()));
	}
}
