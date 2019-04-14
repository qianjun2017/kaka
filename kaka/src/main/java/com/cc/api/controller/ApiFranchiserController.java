/**
 * 
 */
package com.cc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Response;
import com.cc.franchiser.form.FranchiserQueryForm;
import com.cc.franchiser.result.FranchiserResult;
import com.cc.franchiser.service.FranchiserService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/franchiser")
public class ApiFranchiserController {
	
	@Autowired
	private FranchiserService franchiserService;

	/**
	 * 查询区域的经销商
	 * @param locationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/location/{locationId:\\d+}", method = RequestMethod.POST)
	public Response<Object> queryFranchiserByLocationId(@PathVariable Long locationId){
		Response<Object> response = new Response<Object>();
		FranchiserQueryForm form = new FranchiserQueryForm();
		form.setLocationId(locationId);
		List<FranchiserResult> franchiserList = franchiserService.queryFranchiserList(form);
		if(ListTools.isEmptyOrNull(franchiserList)){
			response.setMessage("查询地区没有经销商");
			return response;
		}
		response.setData(franchiserList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
