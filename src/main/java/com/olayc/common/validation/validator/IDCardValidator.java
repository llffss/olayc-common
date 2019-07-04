package com.olayc.common.validation.validator;

import com.olayc.common.validation.constraints.PhoneNo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IDCardValidator implements ConstraintValidator<PhoneNo, String> {

	private final String ID_CARD_REGEX1 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
	private final String ID_CARD_REGEX2 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (isNotEmpty(value) && (value.matches(ID_CARD_REGEX1))||value.matches(ID_CARD_REGEX2)) {
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