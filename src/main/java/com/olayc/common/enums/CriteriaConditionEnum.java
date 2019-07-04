package com.olayc.common.enums;

import lombok.Getter;

/**
 * 描述：查询条件码枚举
 * @author jianbiao11
 * @date 2018-08-08
 **/
@Getter
public enum CriteriaConditionEnum {
	/*等于*/
	EQ("eq", "等于"), /*不等于*/
	NEQ("neq", "不等于"), /*大于*/
	GT("gt", "大于"), /*大于等于*/
	EGT("egt", "大于等于"), /*小于*/
	LT("lt", "小于"), /*小于等于*/
	ELT("elt", "小于等于"), /*like*/
	LIKE("like", "like"), /*between*/
	BETWEEN("between", "between"),;

	private String code;
	private String message;

	CriteriaConditionEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * 根据value返回枚举类型,主要在switch中使用
	 * @param value
	 * @return
	 */
	public static CriteriaConditionEnum getByValue(String value) {
		for (CriteriaConditionEnum condition : values()) {
			if (condition.getCode().equalsIgnoreCase(value)) {
				return condition;
			}
		}
		return null;
	}

}
