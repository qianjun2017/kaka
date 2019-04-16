/**
 * 
 */
package com.cc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.BespeakForm;
import com.cc.common.exception.LogicException;
import com.cc.common.web.Response;
import com.cc.consultant.bean.ConsultantBean;
import com.cc.customer.bean.BespeakBean;
import com.cc.customer.service.BespeakService;
import com.cc.franchiser.bean.FranchiserBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/bespeak")
public class ApiBespeakController {

	@Autowired
	private BespeakService bespeakService;
	
	/**
	 * 预约看车
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response<String> addBespeak(@RequestBody BespeakForm form){
		Response<String> response = new Response<String>();
		BespeakBean bespeakBean = new BespeakBean();
		bespeakBean.setCustomerId(form.getCustomerId());
		if(form.getConsultantId()==null){
			response.setMessage("请选择汽车顾问");
			return response;
		}
		ConsultantBean consultantBean = ConsultantBean.get(ConsultantBean.class, form.getConsultantId());
		if(consultantBean==null){
			response.setMessage("汽车顾问不存在，请重新选择");
			return response;
		}
		bespeakBean.setConsultantId(form.getConsultantId());
		if(form.getFranchiserId()==null){
			response.setMessage("请选择经销商");
			return response;
		}
		FranchiserBean franchiserBean = FranchiserBean.get(FranchiserBean.class, form.getFranchiserId());
		if(franchiserBean==null){
			response.setMessage("经销商不存在，请重新选择");
			return response;
		}
		bespeakBean.setFranchiserId(form.getFranchiserId());
		if(form.getBespeakTime()==null){
			response.setMessage("请选择看车时间");
			return response;
		}
		bespeakBean.setBespeakTime(form.getBespeakTime());
		try {
			bespeakService.saveBespeak(bespeakBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
