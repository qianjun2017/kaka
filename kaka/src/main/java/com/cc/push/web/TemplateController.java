/**
 * 
 */
package com.cc.push.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.push.bean.TemplateBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryListResult;
import com.cc.push.service.TemplateService;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	/**
	 * 分页获取帐号下已存在的模板列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<TemplateBean> queryTemplatePage(@ModelAttribute TemplateQueryFrom form){
		Page<TemplateBean> page = templateService.queryTemplatePage(form);
		return page;
	}
	
	/**
	 * 分页获取小程序模板库标题列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/library/page", method = RequestMethod.GET)
	public Page<TemplateLibraryListResult> queryTemplateLibraryPage(@ModelAttribute TemplateLibraryQueryFrom form){
		Page<TemplateLibraryListResult> page = templateService.queryTemplateLibraryPage(form);
		return page;
	}
	
	/**
	 * 同步帐号下已存在的模板列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public Response<String> syncTemplate(){
		Response<String> response = new Response<String>();
		templateService.syncTemplate();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
