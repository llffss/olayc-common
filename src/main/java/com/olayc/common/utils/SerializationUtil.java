package com.olayc.common.utils;

import java.io.*;

/**
 * 对象序列化工具。
 * @author suntao
 * @version V3.0
 * @date 2015-8-30 上午10:45:36
 *
 */
public class SerializationUtil {

	/**
	 * 转为bytes。
	 *
	 * @author suntao
	 * @date 2015-8-29 下午2:47:38
	 *
	 * @param obj
	 * @return
	 */
	public static byte[] toBytes(Object obj) throws IOException {
		byte[] bytes = null;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		bytes = bos.toByteArray();
		oos.close();
		bos.close();


		return bytes;
	}

	/**
	 * 转为对象。
	 *
	 * @author suntao
	 * @date 2015-8-29 下午2:32:47
	 *
	 * @param bytes
	 * @return
	 */
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object obj = null;

		if (bytes != null) {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		}


		return obj;
	}
}
