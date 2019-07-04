package com.olayc.common.constant;

/**
 * 通用的结果常量
 *
 * @author liuyan
 * @date 2018-08-22
 */
public  enum CommonResultEnum implements BaseEnum {

    /**
     * 接口返回状态码定义。
     */

    SUCCESS_RESULT_CODE(0,"业务成功"),
    FAIL_RESULT_CODE(999,"业务失败"),


    ILLEGAL_ARGS_RESULT_CODE(400,"参数错误"),
    NOT_LOGIN_RESULT_CODE(401,"未登录"),
    AUTH_FAIL(403,"权限错误"),
    API_NOT_FOUND_RESULT_CODE(404,"接口不存在"),


    PARAM_ERROR(500,"系统错误");

    private Integer code;
    private String message;

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public void setCode(Integer code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    CommonResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResultEnum getEnum(Integer value) {
        CommonResultEnum[] businessModeEnums = values();
        for (CommonResultEnum businessModeEnum : businessModeEnums) {
            if (businessModeEnum.getCode().equals(value)) {
                return businessModeEnum;
            }
        }
        return FAIL_RESULT_CODE;
    }
}
