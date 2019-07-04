package com.olayc.common.service;

import com.olayc.common.msg.TableResultResponse;
import com.olayc.common.vo.Query;
import com.olayc.common.vo.PageList;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.InvocationTargetException;

/**
 * 描述：
 * 为前后端分离后的前端提供数据支撑，加入了前端表格的数据封装，
 * 以及保存、更新操作的通用字段处理
 * @author jianbiao11
 * Date  2018-06-07
 **/
public class BaseAdminService<M extends Mapper<T>, T> extends BaseService<M, T> {

	@Override
	public int insert(T entity) {
		//TODO:这里以后加入 通用字段的处理
		return mapper.insert(entity);
	}

	@Override
	public int insertSelective(T entity) {
		//TODO:这里以后加入 通用字段的处理
		return mapper.insertSelective(entity);
	}

	@Override
	public int updateEntity(T entity) {
		return mapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public int updateSelectiveById(T entity) {
		//TODO:这里以后加入 通用字段的处理
		return mapper.updateByPrimaryKeySelective(entity);
	}

	/**
	 * 封装表现层的Table
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TableResultResponse selectByQuery(Query query)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		PageList pageList = this.selectList4PageList(query);
		return new TableResultResponse(pageList.getTotal(), pageList.getList());
	}

}
