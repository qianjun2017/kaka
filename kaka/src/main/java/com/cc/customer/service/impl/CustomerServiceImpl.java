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
import com.cc.customer.dao.CustomerDao;
import com.cc.customer.enums.CustomerStatusEnum;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.result.CustomerListResult;
import com.cc.customer.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveCustomer(CustomerBean customerBean) {
		int row = customerBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存会员失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void lockCustomer(Long id) {
		CustomerBean customerBean = new CustomerBean();
		customerBean.setId(id);
		customerBean.setStatus(CustomerStatusEnum.LOCKED.getCode());
		int row = customerBean.update();
		if(row!=1){
			throw new LogicException("E001", "锁定会员失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void unLockCustomer(Long id) {
		CustomerBean customerBean = new CustomerBean();
		customerBean.setId(id);
		customerBean.setStatus(CustomerStatusEnum.NORMAL.getCode());
		int row = customerBean.update();
		if(row!=1){
			throw new LogicException("E001", "锁定会员失败");
		}
	}

	@Override
	public Page<CustomerListResult> queryCustomerPage(CustomerQueryForm form) {
		Page<CustomerListResult> page = new Page<CustomerListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<CustomerListResult> customerList = customerDao.queryCustomerList(form);
		PageInfo<CustomerListResult> pageInfo = new PageInfo<CustomerListResult>(customerList);
		if (ListTools.isEmptyOrNull(customerList)) {
			page.setMessage("没有查询到相关会员数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(customerList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
