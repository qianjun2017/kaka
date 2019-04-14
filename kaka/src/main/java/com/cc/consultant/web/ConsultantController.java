/**
 * 
 */
package com.cc.consultant.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardBean;
import com.cc.consultant.bean.ConsultantBean;
import com.cc.consultant.service.ConsultantService;
import com.cc.consultant.form.ConsultantQueryForm;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/consultant")
public class ConsultantController {
	
	@Autowired
	private ConsultantService consultantService;

	/**
	 * 删除汽车顾问
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CONSULTANTMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除汽车顾问", paramNames = {"id"})
	public Response<Object> deleteConsultant(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		ConsultantBean consultantBean = ConsultantBean.get(ConsultantBean.class, id);
		if(consultantBean==null){
			response.setMessage("汽车顾问不存在或已删除");
			return response;
		}
		try {
			consultantService.deleteConsultant(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 新增汽车顾问
	 * @param consultantMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CONSULTANTMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增汽车顾问")
	public Response<Object> addConsultant(@RequestBody Map<String, Object> consultantMap){
		Response<Object> response = new Response<Object>();
		ConsultantBean consultantBean = JsonTools.toObject(JsonTools.toJsonString(consultantMap), ConsultantBean.class);
		if(StringTools.isNullOrNone(consultantBean.getName())){
			response.setMessage("请输入汽车顾问姓名");
			return response;
		}
		if(StringTools.isNullOrNone(consultantBean.getPhone())){
			response.setMessage("请输入汽车顾问手机号码");
			return response;
		}
		if(!StringTools.matches(consultantBean.getPhone(), "^1[34578]\\d{9}$")){
			response.setMessage("请输入11位有效手机号码");
            return response;
		}
		if (StringTools.isNullOrNone(consultantBean.getImageUrl())) {
			response.setMessage("请上传汽车顾问头像");
			return response;
		}
		try {
			consultantService.saveConsultant(consultantBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 修改汽车顾问
	 * @param consultantMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CONSULTANTMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改汽车顾问")
	public Response<Object> updateConsultant(@RequestBody Map<String, Object> consultantMap){
		Response<Object> response = new Response<Object>();
		Object id = consultantMap.get("id");
		if(id==null){
			response.setMessage("缺少汽车顾问主键");
			return response;
		}
		ConsultantBean oldConsultantBean = CardBean.get(ConsultantBean.class, Long.valueOf(StringTools.toString(id)));
		if(oldConsultantBean==null){
			response.setMessage("汽车顾问不存在或已删除");
			return response;
		}
		ConsultantBean consultantBean = JsonTools.toObject(JsonTools.toJsonString(consultantMap), ConsultantBean.class);
		if(StringTools.isNullOrNone(consultantBean.getName())){
			response.setMessage("请输入汽车顾问姓名");
			return response;
		}
		oldConsultantBean.setName(consultantBean.getName());
		if(StringTools.isNullOrNone(consultantBean.getPhone())){
			response.setMessage("请输入汽车顾问手机号码");
			return response;
		}
		if(!StringTools.matches(consultantBean.getPhone(), "^1[34578]\\d{9}$")){
			response.setMessage("请输入11位有效手机号码");
            return response;
		}
		oldConsultantBean.setPhone(consultantBean.getPhone());
		if (StringTools.isNullOrNone(consultantBean.getImageUrl())) {
			response.setMessage("请上传汽车顾问头像");
			return response;
		}
		oldConsultantBean.setImageUrl(consultantBean.getImageUrl());
		try {
			consultantService.saveConsultant(oldConsultantBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询汽车顾问详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ConsultantBean> queryConsultant(@PathVariable Long id){
		Response<ConsultantBean> response = new Response<ConsultantBean>();
		ConsultantBean consultantBean = ConsultantBean.get(ConsultantBean.class, id);
		if(consultantBean==null){
			response.setMessage("汽车顾问不存在或已删除");
			return response;
		}
		response.setData(consultantBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询汽车顾问信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<ConsultantBean> queryConsultantPage(@ModelAttribute ConsultantQueryForm form){
		return consultantService.queryConsultantPage(form);
	}
}
