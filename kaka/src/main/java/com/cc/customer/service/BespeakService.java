/**
 * 
 */
package com.cc.customer.service;

import com.cc.common.web.Page;
import com.cc.customer.bean.BespeakBean;
import com.cc.customer.form.BespeakQueryForm;
import com.cc.customer.result.BespeakListResult;

/**
 * @author Administrator
 *
 */
public interface BespeakService {

	/**
	 * 保存预约看车记录
	 * @param bespeakBean
	 */
	void saveBespeak(BespeakBean bespeakBean);
	
	/**
	 * 分页查询预约看车记录
	 * @param form
	 * @return
	 */
	Page<BespeakListResult> queryBespeakPage(BespeakQueryForm form);
}
