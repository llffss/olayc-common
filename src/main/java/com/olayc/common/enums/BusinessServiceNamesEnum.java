package com.olayc.common.enums;

/**
 * 核心业务的名称枚举
 * @author jianbiao11
 */
public enum BusinessServiceNamesEnum {


    TOUR("TOUR", "用车行程业务"),
	COUPON("COUPON", "优惠券业务"),
	ACTIVITY("ACTIVITY", "活动业务"),
	CHARGING("CHARGING", "计费业务"),
	CUSTOMER("CUSTOMER", "用户信息业务"),
	ACCOUNT("ACCOUNT", "账户信息业务"),
	VEHICLE("VEHICLE", "车辆业务"),
	PAYMENT("PAYMENT", "支付类业务"),
	GATEWAY("GATEWAY", "通用业务"),
	OTHER("OTHER", "通用业务"),
	;

    private String code;
    private String message;

    BusinessServiceNamesEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public String getCode() {
        return code;
    }
}
