/**
 * 
 */
package com.cc.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.dao.PointsDao;
import com.cc.customer.form.PointsQueryForm;
import com.cc.customer.result.PointsListResult;
import com.cc.customer.service.PointsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, pointsBean.getCustomerId());
		if(customerBean.getPoints()+pointsBean.getPoints()<0){
			throw new LogicException("E001", "积分不足");
		}
		int row = pointsDao.updateCustomerPoints(pointsBean);
		if(row!=1){
			throw new LogicException("E002", "变更会员积分失败");
		}
		row = pointsBean.save();
		if(row!=1){
			throw new LogicException("E003", "保存积分变更失败");
		}
	}

	@Override
	public Page<PointsListResult> queryPointsPage(PointsQueryForm form) {
		Page<PointsListResult> page = new Page<PointsListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<PointsListResult> pointsList = pointsDao.queryPointsList(form);
		PageInfo<PointsListResult> pageInfo = new PageInfo<PointsListResult>(pointsList);
		if (ListTools.isEmptyOrNull(pointsList)) {
			page.setMessage("没有查询到相关积分变更数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(pointsList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
