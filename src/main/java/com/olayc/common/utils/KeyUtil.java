package com.olayc.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jun
 * @date 2018/8/2 14:15
 * @company olasharing.com
 */
public class KeyUtil {

	private static final String EX_CACHE = "ex.cache$";

	/**
	 * 生成一个key
	 * @param clazzName 类名
	 * @param methodName 方法名
	 * @param returnName 返回值名称
	 * @param paramType 参数类型名称集合
	 * @param paramValues 参数集合
	 * @param value 自定义key
	 * @return
	 */
	public static String getKey(String clazzName, String methodName, String returnName, Object[] paramType,
			Object[] paramValues, String value) {
		StringBuilder keyBuf = new StringBuilder();

		keyBuf.append(EX_CACHE);
		keyBuf.append(clazzName + "$");
		keyBuf.append(methodName + "$");
		if (StringUtils.isNotBlank(returnName)) {
			keyBuf.append(returnName + "$");
		}
		if (paramType != null) {
			keyBuf.append(JsonUtils.toJsonString(paramType) + "$");
		}
		if (paramValues != null) {
			keyBuf.append(JsonUtils.toJsonString(paramValues) + "$");
		}
		if (StringUtils.isNotBlank(value)) {
			keyBuf.append(value);
		}
		return keyBuf.toString();
	}


	/**
	 * 提取key到map中
	 * @param key
	 * @return
	 */
	public static Map<String, String> getKey2Map(String key) {
		if (key.indexOf("$") == -1) {
			return null;
		}
		String[] keySplit = key.split("$");
		if (!keySplit[0].equalsIgnoreCase(EX_CACHE) || keySplit.length < 6) {
			return null;
		}
		String clazzName = keySplit[1];
		String methodName = keySplit[2];
		String returnName = keySplit[3];
		String paramTypeJson = keySplit[4];
		String paramValueJson = keySplit[5];
		String value = null;
		if (keySplit.length >= 7) {
			value = keySplit[6];
		}

		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("clazzName", clazzName);
		resultMap.put("methodName", methodName);
		resultMap.put("returnName", returnName);
		resultMap.put("paramTypeJson", paramTypeJson);
		resultMap.put("paramValueJson", paramValueJson);
		resultMap.put("value", value);
		return resultMap;
	}

}
