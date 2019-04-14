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
import com.cc.customer.bean.BespeakBean;
import com.cc.customer.dao.BespeakDao;
import com.cc.customer.form.BespeakQueryForm;
import com.cc.customer.result.BespeakListResult;
import com.cc.customer.service.BespeakService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class BespeakServiceImpl implements BespeakService {
	
	@Autowired
	private BespeakDao bespeakDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveBespeak(BespeakBean bespeakBean) {
		int row = bespeakBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存预约看车失败");
		}
	}

	@Override
	public Page<BespeakListResult> queryBespeakPage(BespeakQueryForm form) {
		Page<BespeakListResult> page = new Page<BespeakListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<BespeakListResult> bespeakList = bespeakDao.queryBespeakList(form);
		PageInfo<BespeakListResult> pageInfo = new PageInfo<BespeakListResult>(bespeakList);
		if (ListTools.isEmptyOrNull(bespeakList)) {
			page.setMessage("没有查询到相关预约看车数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(bespeakList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
