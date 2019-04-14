/**
 * 
 */
package com.cc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Page;
import com.cc.consultant.bean.ConsultantBean;
import com.cc.consultant.form.ConsultantQueryForm;
import com.cc.consultant.service.ConsultantService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/consultant")
public class ApiConsultantController {
	
	@Autowired
	private ConsultantService consultantService;

	/**
	 * 分页查询汽车顾问信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Page<ConsultantBean> queryConsultantPage(@RequestBody ConsultantQueryForm form){
		return consultantService.queryConsultantPage(form);
	}
}
