package com.cc.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.form.PointsQueryForm;
import com.cc.customer.result.PointsListResult;

@Mapper
public interface PointsDao extends CrudDao {

	/**
	 * 变更会员积分
	 * @param pointsBean
	 * @return
	 */
	int updateCustomerPoints(PointsBean pointsBean);
	
	/**
	 * 查询积分变更
	 * @param form
	 * @return
	 */
	List<PointsListResult> queryPointsList(PointsQueryForm form);
}
