/**
 * 
 */
package com.cc.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Response<Object> test(@RequestBody String name){
		Response<Object> response = new Response<Object>();
		System.out.println("请求参数: " + name);
		response.setData("返回数据");
		return response;
	}
}
