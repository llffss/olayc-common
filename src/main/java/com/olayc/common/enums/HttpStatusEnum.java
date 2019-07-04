package com.olayc.common.enums;

import lombok.Getter;

/**
 * 描述：http 状态码枚举
 * @author jianbiao11
 * @date 2018-08-08
 **/
@Getter
public enum HttpStatusEnum {
	/*服务器成功返回用户请求的数据*/
	OK(200, "请求成功"), /*用户新建或修改数据成功*/
	CREATED(201, "新建或修改数据成功"), /*表示一个请求已经进入后台排队（异步任务）*/
	ACCEPTED(202, "异步任务接收完成"), /*用户删除数据成功*/
	NOCONTENT(204, "删除数据成功"), /* 服务器没有进行新建或修改数据的操作*/
	INVALID(400, "用户发出的请求有错误"), /*表示用户没有权限（令牌、用户名、密码错误）*/
	UNAUTHORIZED(401, "用户没有权限"), /*表示用户得到授权（与401错误相对），但是访问是被禁止的*/
	FORBIDDEN(403, "访问是被禁止的"), /*用户发出的请求针对的是不存在的记录，服务器没有进行操作*/
	NOTFOUND(404, "资源没找到"), /*当创建一个对象时，发生一个验证错误*/
	UNPROCESABLEENTITY(422, "创建对象失败,验证失败"), /*服务错误*/
	ServerError(500, "服务器错误"),;

	private Integer code;
	private String message;

	HttpStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
