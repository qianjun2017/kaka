/**
 * 
 */
package com.cc.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.dao.PointsDao;
import com.cc.customer.service.PointsService;

/**
 * @author Administrator
 *
 */
@Service
public class PointsServiceImpl implements PointsService {
	
	@Autowired
	private PointsDao pointsDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void savePoints(PointsBean pointsBean) {
		int row = pointsDao.updateCustomerPoints(pointsBean);
		if(row!=1){
			throw new LogicException("E001", "变更会员积分失败");
		}
		row = pointsBean.save();
		if(row!=1){
			throw new LogicException("E002", "保存积分变更失败");
		}
	}

}
