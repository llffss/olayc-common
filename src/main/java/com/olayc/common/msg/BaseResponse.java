package com.olayc.common.msg;

import com.olayc.common.constant.BaseEnum;
import com.olayc.common.constant.CommonResultEnum;

import java.util.Collections;

/**
 * 描述： 统一返回消息类
 *
 * @author jianbiao11
 * Date  2018-06-07
 **/
public class BaseResponse<T> extends BaseToString {

	private static final long serialVersionUID = 3248300180133428071L;

	public BaseResponse(){ }

	/**
     * 状态码 默认 0:成功 999：失败
     */
    private Integer code ;

    /**
     * 提示信息
     */
    private String msg = "操作成功";

    /**
     * 具体内容
     */
    private T data;

    /*总记录*/
    private Integer count;
    /*总页数*/
    private Integer pages;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static BaseResponse successResponse() {
        return successResponse(Collections.emptyMap());
    }

    public BaseResponse(BaseEnum result) {
        this.code=result.getCode();
        this.msg=result.getMessage();
    }

    public static <T> BaseResponse<T> successResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>(CommonResultEnum.SUCCESS_RESULT_CODE);
        response.setData(data);
        return response;
    }

    /**
     *
     * @param resultEnum
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> failResponse(BaseEnum resultEnum) {
        BaseResponse<T> response = new BaseResponse<>(resultEnum);
        return response;
    }

}
