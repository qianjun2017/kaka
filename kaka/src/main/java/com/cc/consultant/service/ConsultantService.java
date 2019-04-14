package com.cc.consultant.service;

import com.cc.common.web.Page;
import com.cc.consultant.bean.ConsultantBean;
import com.cc.consultant.form.ConsultantQueryForm;

public interface ConsultantService {

	/**
	 * 保存汽车顾问
	 * @param consultantBean
	 */
	void saveConsultant(ConsultantBean consultantBean);
	
	/**
	 * 删除汽车顾问
	 * @param id
	 */
	void deleteConsultant(Long id);

	/**
	 * 查询汽车顾问
	 * @param form
	 * @return
	 */
	Page<ConsultantBean> queryConsultantPage(ConsultantQueryForm form);
}
