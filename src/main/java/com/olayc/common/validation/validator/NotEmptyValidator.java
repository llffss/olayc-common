package com.olayc.common.validation.validator;

import com.olayc.common.validation.constraints.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (isNotEmpty(value, true)) {
			return true;
		}
		return false;
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
	private boolean isNotEmpty(Object obj, boolean b) {
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
}