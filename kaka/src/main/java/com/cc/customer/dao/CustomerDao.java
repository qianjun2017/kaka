/**
 * 
 */
package com.cc.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.result.CustomerListResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface CustomerDao extends CrudDao {

	/**
	 * 查询会员列表
	 * @param form
	 * @return
	 */
	List<CustomerListResult> queryCustomerList(CustomerQueryForm form);
}
