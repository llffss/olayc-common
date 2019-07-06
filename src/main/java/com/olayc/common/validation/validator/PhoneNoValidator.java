package com.olayc.common.validation.validator;

import com.olayc.common.validation.constraints.PhoneNo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneNoValidator implements ConstraintValidator<PhoneNo, String> {

	private final String phoneNoRegex = "^1([1-9]\\d|5[0-35-9]|7[3678])\\d{8}$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (isNotEmpty(value) && value.matches(phoneNoRegex)) {
			return true;
		}
		return false;
	}

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
	private boolean isEmpty(String str) {
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
	private boolean isNotEmpty(Object obj) {
		if (obj == null) {
			return false;
		} else {
			return !isEmpty(String.valueOf(obj));
		}
	}

}