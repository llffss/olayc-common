package com.olayc.common.constant;

/**
 * 健康检查常量
 *
 * @author jianbiao11
 * @date 2018-10-16
 */
public class CommonConstants {

	/**
	 * 健康检查报文体
	 */
	public final static String OK_RESULT = "ok";

	/**
	 * 健康检查报文体
	 */
	public final static String FAIL_RESULT = "fail";

	/**
	 * 优雅下线接口
	 */
	public static final String OUT_OF_SERVICE_URL="/service/offline";

	/**
	 * 优雅下线接口地址
	 */
    public static final String OUT_OF_SERVICE_EUREKA_URL="apps/%s/%s/status?value=OUT_OF_SERVICE";

	/**
	 * 直接删除实例
	 */
	public static final String DELETE_SERVICE_EUREKA_URL="apps/%s/%s";


}
