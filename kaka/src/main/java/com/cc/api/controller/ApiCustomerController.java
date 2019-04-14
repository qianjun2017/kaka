/**
 * 
 */
package com.cc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.JsonTools;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.result.CustomerResult;
import com.cc.customer.service.CardService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/customer")
public class ApiCustomerController {
	
	@Autowired
	private CardService cardService;

	/**
	 * 查询登录用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Response<Object> queryCustomerInfo(){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, RequestContextUtil.getCustomerId());
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
}
