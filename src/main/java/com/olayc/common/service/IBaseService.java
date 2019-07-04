package com.olayc.common.service;

import com.olayc.common.exception.DbHandleException;
import com.olayc.common.vo.PageList;
import com.olayc.common.vo.Query;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 描述：通用service 接口
 * @author jianbiao11
 * Date  2018-06-07
 **/
public interface IBaseService<T> {

	/**
	 * 查询单条数据
	 * @param entity
	 * @return
	 */
	public T selectOne(T entity);

	/**
	 * 通过主键查询单条数据
	 * @param id
	 * @return
	 */
	public T selectById(Object id);

	/**
	 * 通过实体类条件查询列表
	 * @param entity
	 * @return
	 */
	public List<T> selectList(T entity);

	/**
	 * 查询列表
	 * @param entity
	 * @return
	 */
	public List<T> selectListAll();

	/**
	 * 通过实体类条件查询列表条数
	 * @param entity
	 * @return
	 */
	public Long selectCount(T entity);

	/**
	 * 保存一个实体，null的属性也会保存，不会使用数据库默认值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	public int insert(T entity) throws DbHandleException;

	/**
	 *保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	public int insertSelective(T entity) throws DbHandleException;

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	public int delete(T entity) throws DbHandleException;

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @param id
	 * @throws DbHandleException
	 * @return
	 */
	public int deleteById(Object id) throws DbHandleException;

	/**
	 *  根据主键更新属性不为null的值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	public int updateEntity(T entity) throws DbHandleException;

	/**
	 * 根据主键更新属性不为null的值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	public int updateSelectiveById(T entity) throws DbHandleException;

	/**
	 * 查询数据列表以分页方式返回
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<T> selectList4Page(int pageNo, int pageSize);

	/**
	 * 分页查询列表，以PageList包装返回
	 * @param query  查询条件
	 * @return
	 */
	public PageList selectList4PageList(Query query)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

}
