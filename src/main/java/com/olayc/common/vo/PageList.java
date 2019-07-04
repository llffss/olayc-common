package com.olayc.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询后的数据
 *@author jianbiao11
 *@date 2018/8/14 13:41
 */
public class PageList<Object> implements Serializable {

	private static final long serialVersionUID = 4784948454478430557L;

	public PageList() {
	}

	public PageList(long total, List<Object> list) {
		this.total = total;
		this.list = list;
	}

	private long total;
	private List<Object> list;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
}
