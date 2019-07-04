package com.olayc.common.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.olayc.common.exception.DbHandleException;
import com.olayc.common.enums.CriteriaConditionEnum;
import com.olayc.common.utils.StringUtil;
import com.olayc.common.vo.PageList;
import com.olayc.common.vo.Query;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：通用service类
 * @author jianbiao11
 * Date  2018-06-07
 **/
public abstract class BaseService<M extends Mapper<T>, T> extends BaseTxService implements IBaseService<T> {

	@Autowired
	protected M mapper;

	/**
	 * 查询单条数据
	 * @param entity
	 * @return
	 */
	@Override
	public T selectOne(T entity) {
		return mapper.selectOne(entity);
	}

	/**
	 * 通过主键查询单条数据
	 * @param id
	 * @return
	 */
	@Override
	public T selectById(Object id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * 通过实体类条件查询列表
	 * @param entity
	 * @return
	 */
	@Override
	public List<T> selectList(T entity) {
		return mapper.select(entity);
	}

	/**
	 * 查询列表
	 * @return
	 */
	@Override
	public List<T> selectListAll() {
		return mapper.selectAll();
	}

	/**
	 * 通过实体类条件查询列表条数
	 * @param entity
	 * @return
	 */
	@Override
	public Long selectCount(T entity) {
		return Long.valueOf(mapper.selectCount(entity));
	}

	/**
	 * 保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int insert(T entity) throws DbHandleException {
		return this.insertSelective(entity);
	}

	/**
	 *保存一个实体，null的属性不会保存，会使用数据库默认值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int insertSelective(T entity) throws DbHandleException {
		int result = mapper.insertSelective(entity);
		if (result == 0) {
			throw new DbHandleException("insert entity error");
		}
		return result;
	}

	/**
	 * 根据实体属性作为条件进行删除，查询条件使用等号
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int delete(T entity) throws DbHandleException {
		int result = mapper.delete(entity);
		if (result == 0) {
			throw new DbHandleException("delete entity error");
		}
		return result;
	}

	/**
	 * 根据主键字段进行删除，方法参数必须包含完整的主键属性
	 * @param id
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int deleteById(Object id) throws DbHandleException {
		int result = mapper.deleteByPrimaryKey(id);
		if (result == 0) {
			throw new DbHandleException("delete entity error");
		}
		return result;
	}

	/**
	 *  根据主键更新属性不为null的值
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int updateEntity(T entity) {
		int result = mapper.updateByPrimaryKeySelective(entity);
		if (result == 0) {
			throw new DbHandleException("delete entity error");
		}
		return result;
	}

	/**
	 * 根据主键更新属性不为null的值 同 updateEntity
	 * @param entity
	 * @throws DbHandleException
	 * @return
	 */
	@Override
	public int updateSelectiveById(T entity) {
		return this.updateEntity(entity);
	}

	/**
	 * 查询数据列表以分页方式返回
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<T> selectList4Page(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return mapper.selectAll();
	}

	public List<T> selectByExample(Object example) {
		return mapper.selectByExample(example);
	}

	public int selectCountByExample(Object example) {
		return mapper.selectCountByExample(example);
	}

	/**
	 * 处理带条件的查询
	 * @param query  查询条件
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	@Override
	public PageList selectList4PageList(Query query)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		Example example = new Example(clazz);
		String[] fieldsArray = new String[0];

		if (query.entrySet().size() > 0) {
			Example.Criteria criteria = example.createCriteria();
			for (Map.Entry<String, Object> entry : query.entrySet()) {
				//处理排序
				if ("sort".equalsIgnoreCase(entry.getKey())) {
					String sortInfo = entry.getValue().toString();
					String sortClausStr = sortClauseHandler(sortInfo);
					if (StringUtil.isNotEmpty(sortClausStr)) {
						example.setOrderByClause(sortClausStr);
					}
				} else if ("fields".equalsIgnoreCase(entry.getKey())) {
					String fieldsInfo = entry.getValue().toString();
					if (!StringUtil.isEmpty(fieldsInfo)) {
						List<String> condionList = StringUtil.splitToList(",", fieldsInfo);
						fieldsArray = new String[condionList.size()];
						example.selectProperties(condionList.toArray(fieldsArray));
					}
				} else {
					//判断筛选条件是否为空
					checkAssemblyConditions(criteria, entry);
				}
			}
		}
		Page<Object> result = PageHelper.startPage(query.getPageNo(), query.getPageSize());
		List<T> list = mapper.selectByExample(example);

		List<Object> listReturn = new ArrayList<>();
		if (fieldsArray.length > 0) {
			for (T entity : list) {
				Map<String, Object> map = new HashMap<>(0);
				for (String name : fieldsArray) {
					Object value = null;
					value = PropertyUtils.getSimpleProperty(entity, name);
					map.put(name, value);
				}
				listReturn.add(map);
			}
			return new PageList<Object>(result.getTotal(), listReturn);
		} else {
			return new PageList<T>(result.getTotal(), list);
		}
	}

	/**
	 * 处理排序参数
	 * <p>
	 *     sort=-id  => id desc
	 *     sort=-id,+name => id desc,name asc
	 * </p>
	 * @param sortInfo
	 */
	private String sortClauseHandler(String sortInfo) {
		List<String> sortList = StringUtil.splitToList(",", sortInfo);
		StringBuffer sortClauseSb = new StringBuffer();
		//遍历排序字符
		for (String sort : sortList) {
			String sortFlag = "0";
			boolean isSign = false;
			if (sort.startsWith("+")) {
				sortFlag = "ASC";
				isSign = true;
			} else if (sort.startsWith("-")) {
				sortFlag = "DESC";
				isSign = true;
			} else {
				sortFlag = "ASC";
				isSign = false;
			}
			String sortFlied = sort;
			if (isSign) {
				sortFlied = sort.substring(1);
			}
			//驼峰转字段
			sortClauseSb.append(StringUtil.underscoreName(sortFlied)).append(" ").append(sortFlag).append(",");
		}
		String sortClauseStr = sortClauseSb.toString().substring(0, sortClauseSb.toString().length() - 1);
		return sortClauseStr;
	}

	/**
	 * 处理筛选条件
	 * @param criteria
	 * @param entry
	 */
	private void checkAssemblyConditions(Example.Criteria criteria, Map.Entry<String, Object> entry) {
		if (StringUtil.isNotEmpty(entry.getValue().toString())) {
			List<String> conditionList = StringUtil.splitTwoParts("-", entry.getValue().toString());
			if (conditionList.size() > 1) {
				String conditionFlag = conditionList.get(0);
				CriteriaConditionEnum conditionEnum = CriteriaConditionEnum.getByValue(conditionFlag);
				//如果传入的条件参数非法则按like查询
				if (conditionEnum == null) {
					criteria.andLike(entry.getKey(), "%" + conditionList.get(1) + "%");
					return;
				}
				switch (conditionEnum) {
					case EQ:
						criteria.andEqualTo(entry.getKey(), conditionList.get(1));
						break;
					case NEQ:
						criteria.andCondition(entry.getKey() + " <> " + conditionList.get(1));
						break;
					case GT:
						criteria.andGreaterThan(entry.getKey(), conditionList.get(1));
						break;
					case EGT:
						criteria.andGreaterThanOrEqualTo(entry.getKey(), conditionList.get(1));
						break;
					case LT:
						criteria.andLessThan(entry.getKey(), conditionList.get(1));
						break;
					case ELT:
						criteria.andLessThanOrEqualTo(entry.getKey(), conditionList.get(1));
						break;
					case LIKE:
						criteria.andLike(entry.getKey(), "%" + conditionList.get(1) + "%");
						break;
					case BETWEEN:
						List<String> conditionBetween = StringUtil.splitToList(",", conditionList.get(1));
						criteria.andBetween(entry.getKey(), conditionBetween.get(0), conditionBetween.get(1));
						break;
					default:
						criteria.andLike(entry.getKey(), "%" + conditionList.get(1) + "%");
						break;
				}
			} else {
				criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
			}
		} else {
			return;
		}
	}

}
