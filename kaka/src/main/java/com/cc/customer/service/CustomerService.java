/**
 * 
 */
package com.cc.customer.service;

import com.cc.common.web.Page;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.result.CustomerListResult;

/**
 * @author Administrator
 *
 */
public interface CustomerService {

	/**
	 * 保存客户
	 * @param customerBean
	 */
	void saveCustomer(CustomerBean customerBean);
	
	/**
	 * 锁定客户
	 * @param id
	 */
	void lockCustomer(Long id);
	
	/**
	 * 解锁客户
	 * @param id
	 */
	void unLockCustomer(Long id);
	
	/**
	 * 分页查询客户信息
	 * @param form
	 * @return
	 */
	Page<CustomerListResult> queryCustomerPage(CustomerQueryForm form);
	
}
