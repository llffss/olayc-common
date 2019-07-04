package com.olayc.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 描述：
 * @author jianbiao11
 * Date  2017-07-07
 **/
public class IpUtils {

	public static String getLocalIpString() {
		List<String> list = IpUtils.getLocalIps();
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append("^" + string);
		}
		return sb.toString();
	}

	public static List<String> getLocalIps() {

		List<String> ipList = new ArrayList<String>();
		Enumeration<NetworkInterface> allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						ipList.add(ip.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return ipList;
	}

	/**
	 * 获取请求IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
