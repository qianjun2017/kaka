/**
 * 
 */
package com.cc.customer.web;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.service.CardService;
import com.cc.customer.service.CustomerService;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.result.CustomerListResult;
import com.cc.customer.result.CustomerResult;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CardService cardService;
	
	/**
	 * 查询会员信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<CustomerResult> queryCustomer(@PathVariable Long id){
		Response<CustomerResult> response = new Response<CustomerResult>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		CustomerResult customerResult = JsonTools.toObject(JsonTools.toJsonString(customerBean), CustomerResult.class);
		CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
		if(cardLevelBean!=null){
			customerResult.setCardLevel(cardLevelBean.getName());
			customerResult.setCardImage(cardLevelBean.getImageUrl());
		}
		response.setData(customerResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 锁定会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.lock" })
	@RequestMapping(value = "/lock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.LOCK, title = "锁定会员", paramNames = {"id"})
	public Response<String> lockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		try {
			customerService.lockCustomer(id);
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
	 * 解锁会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.unlock" })
	@RequestMapping(value = "/unlock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.UNLOCK, title = "解锁会员", paramNames = {"id"})
	public Response<String> unlockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		try {
			customerService.unLockCustomer(id);
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
	 * 分页查询会员信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<CustomerListResult> queryCustomerPage(@ModelAttribute CustomerQueryForm form){
		Page<CustomerListResult> page = customerService.queryCustomerPage(form);
		return page;
	}

}
