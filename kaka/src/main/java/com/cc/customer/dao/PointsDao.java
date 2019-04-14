package com.cc.customer.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.customer.bean.PointsBean;

@Mapper
public interface PointsDao extends CrudDao {

	/**
	 * 变更会员积分
	 * @param pointsBean
	 * @return
	 */
	int updateCustomerPoints(PointsBean pointsBean);
}
