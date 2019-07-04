package com.olayc.common.utils;

/**
 * 类型转换
 * @author jianbiao11
 */
public class ConversionUtil {
	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f'};

	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F'};

	/**
	 * 将int转换为4字节数组(高位在前，低位在后)
	 * @author liuyafei
	 * @version V3.0
	 * @date 2015-8-14
	 */
	public static byte[] intToBytes(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) ((value >> 24) & 0xFF);
		src[1] = (byte) ((value >> 16) & 0xFF);
		src[2] = (byte) ((value >> 8) & 0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 * 将int转换为4字节数组(低位在前,高位在后)
	 * @author liuyafei
	 * @version V3.0
	 * @date 2015-9-7
	 */
	public static byte[] intToBytes1(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) (value & 0xFF);
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[2] = (byte) ((value >> 16) & 0xFF);
		src[3] = (byte) ((value >> 24) & 0xFF);
		return src;
	}


	public static byte[] longToBytes(long value) {
		byte[] src = new byte[]{(byte) (value >> 56), (byte) (value >>> 48), (byte) (value >>> 40),
				(byte) (value >>> 32), (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8),
				(byte) value};
		return src;
	}

	/**
	 * 将字节数组转换为int
	 * @author liuyafei
	 * @version V3.0
	 * @date 2015-8-14
	 */
	public static int bytesToInt(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8)
				| (src[offset + 3] & 0xFF));
		return value;
	}

	/**
	 * 获取整数。
	 *
	 * @author suntao
	 * @date 2015-8-21 下午2:34:45
	 *
	 * @param obj
	 * @return
	 */
	public static Integer getInt(Object obj) {
		if (obj == null) {
			return null;
		}

		Integer result = null;

		if (obj instanceof Integer) {
			result = (Integer) obj;
		} else if (obj instanceof Number) {
			Number num = (Number) obj;
			result = num.intValue();
		} else {
			String str = obj.toString().trim();
			if (str.length() > 0) {
				result = Integer.valueOf(obj.toString());
			}
		}

		return result;
	}

	/**
	 * 获取长整数。
	 *
	 * @author suntao
	 * @date 2015-8-21 下午2:34:45
	 *
	 * @param obj
	 * @return
	 */
	public static Long getLong(Object obj) {
		if (obj == null) {
			return null;
		}

		Long result = null;

		if (obj instanceof Long) {
			result = (Long) obj;
		} else if (obj instanceof Number) {
			Number num = (Number) obj;
			result = num.longValue();
		} else {
			String str = obj.toString().trim();
			if (str.length() > 0) {
				result = Long.valueOf(obj.toString());
			}
		}

		return result;
	}

	/**
	 * 获取浮点数。
	 *
	 * @author suntao
	 * @date 2015-8-21 下午2:42:16
	 *
	 * @param obj
	 * @return
	 */
	public static Float getFloat(Object obj) {
		if (obj == null) {
			return null;
		}

		Float result = null;

		if (obj instanceof Float) {
			result = (Float) obj;
		} else if (obj instanceof Number) {
			Number num = (Number) obj;
			result = num.floatValue();
		} else {
			String str = obj.toString().trim();
			if (str.length() > 0) {
				result = Float.valueOf(obj.toString());
			}
		}

		return result;
	}

	/**
	 * 获取Double。
	 *
	 * @author suntao
	 * @date 2015-8-21 下午2:43:19
	 *
	 * @param obj
	 * @return
	 */
	public static Double getDouble(Object obj) {
		if (obj == null) {
			return null;
		}

		Double result = null;

		if (obj instanceof Double) {
			result = (Double) obj;
		} else if (obj instanceof Number) {
			Number num = (Number) obj;
			result = num.doubleValue();
		} else {
			String str = obj.toString().trim();
			if (str.length() > 0) {
				result = Double.valueOf(obj.toString());
			}
		}

		return result;
	}

	/**
	 * 获取String。
	 *
	 * @author suntao
	 * @date 2015-8-27 下午2:13:01
	 *
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return null;
		}

		String str = null;
		if (obj instanceof String) {
			str = (String) obj;
		} else {
			str = obj.toString();
		}

		return str;
	}

	/**
	 *
	 * 根据十六进制数得到十进制
	 * @author liuyafei
	 * @date 2016-1-9 上午11:00:36
	 *
	 * @param str
	 * @return
	 */
	public static Integer getDecimalism(String str) {
		byte[] bytes = decode(str.toCharArray());
		return bytes2int(bytes, true);
	}

	/**
	 * 会员卡号翻转
	 * @author liuyafei
	 * @date 2016-1-9 上午11:13:13
	 *
	 * @param s
	 * @return
	 */
	public static String reverse(String s) {
		String a = s.substring(0, 2);
		String b = s.substring(2, 4);
		String c = s.substring(4, 6);
		String d = s.substring(6, 8);
		s = d + c + b + a;
		return s;
	}


	private static int toDigit(char ch, int index) {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
		}
		return digit;
	}

	public static byte[] decode(char[] data) {

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new RuntimeException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	public static int bytes2int(byte[] bytes, boolean endian) {
		int value = 0;
		if (endian) {
			value = (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
		} else {
			value = (bytes[3] & 0xFF) << 24 | (bytes[2] & 0xFF) << 16 | (bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF);
		}
		return value;
	}

	/**
	 * 根据规则计算会员的memberCardId 值
	 *
	 * @author sky
	 * @date 2016年5月18日 上午10:27:29
	 *
	 * @param areaMemberId
	 * @param areaCode
	 * @param cardType
	 * @return
	 */
	public static Integer getMemberCardId(Integer areaMemberId, Integer areaCode, Integer cardType) {
		return bytes2int(getMemberCardIdByte(areaMemberId, areaCode, cardType), true);
	}

	/**
	 *
	 * 根据会员信息计算会员号
	 * @author sky
	 * @date 2016年5月18日 上午10:19:37
	 *
	 * @param areaMemberId 区域会员序号
	 * @param areaCode  区域码
	 * @param cardType  卡类型
	 * @return 会员号byte数组
	 */
	private static byte[] getMemberCardIdByte(Integer areaMemberId, Integer areaCode, Integer cardType) {
		byte[] src = new byte[4];
		byte areacodeH4 = (byte) (areaCode >> 4 & 0x0F);
		byte cardType4 = (byte) (cardType << 7 & 0x80);
		src[0] = (byte) (areacodeH4 + cardType4);
		//第十六到二十四位
		byte areacodeL4 = (byte) (areaCode << 4 & 0xF0);
		byte memberIdH4 = (byte) ((areaMemberId >> 16) & 0x0F);
		src[1] = (byte) (areacodeL4 + memberIdH4);
		//后十六位
		src[2] = (byte) ((areaMemberId >> 8) & 0xFF);
		src[3] = (byte) (areaMemberId & 0xFF);
		return src;
	}

	/**
	 *
	 * 根据integer 类型的数字转换为需要固定长度的16进制字符串
	 * 不足的位数补零
	 * @author sky
	 * @date 2016年7月18日 上午10:29:10
	 *
	 * @param mval
	 * @param tLength
	 * @return
	 */
	public static String int2Hex(Integer mval, Integer tLength) {
		String hexVal = null;
		if (mval != null && mval instanceof Integer) {
			hexVal = Integer.toHexString(mval).toUpperCase();
			if (hexVal.length() < tLength) {
				int needLength = tLength - hexVal.length();
				String s = "";
				for (int i = 0; i < needLength; i++) {
					s += "0";
				}
				hexVal = s + hexVal;
			} else {
				hexVal = hexVal.substring(0, tLength);
			}
		}
		return hexVal;
	}

	public static byte hexToByte(String hex) {
		int value = toDigit(hex.charAt(0), 0) << 4;
		value = value | toDigit(hex.charAt(1), 0);
		return (byte) (value & 0xFF);
	}

	public static String bytesToHex(byte[] data) {
		return bytesToHex(data, false);
	}

	/**
	 * byte 转换为16进制字符串表示
	 * @param b
	 * @return
	 */
	public static String encode(byte b) {
		char[] out = new char[2];
		out[0] = DIGITS_UPPER[(0xF0 & b) >>> 4];
		out[1] = DIGITS_UPPER[0x0F & b];
		return new String(out);
	}

	private static String bytesToHex(byte[] data, boolean toLowerCase) {
		return bytesToHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	private static String bytesToHex(byte[] data, char[] toDigits) {
		return new String(encode2char(data, toDigits));
	}

	private static char[] encode2char(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}


}
