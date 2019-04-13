/**
 * 
 */
package com.cc.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Response;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController {

	/**
	 * 
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/customer/info", method = RequestMethod.GET)
	public Response<Object> test(){
		Response<Object> response = new Response<Object>();
		response.setData("返回数据");
		return response;
	}
}
