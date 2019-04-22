/**
 * 
 */
package com.cc.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardBean;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/api/card")
public class ApiCardController {

	/**
	 * 获取会员卡细心
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Response<Object> queryCardInfo(){
		Response<Object> response = new Response<Object>();
		List<CardBean> cardBeanList = CardBean.findAllByParams(CardBean.class);
		if(ListTools.isEmptyOrNull(cardBeanList)){
			response.setMessage("没有查询到会员卡信息");
			return response;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		CardBean cardBean = cardBeanList.get(0);
		data.put("id", cardBean.getId());
		data.put("name", cardBean.getName());
		if(cardBean.getRule()!=null && cardBean.getRule().length>0){
			data.put("rule", new String(cardBean.getRule()));
		}
		response.setData(data);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
