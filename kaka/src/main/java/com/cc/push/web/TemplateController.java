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
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateListResult;
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
	
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<TemplateListResult> queryTemplatePage(@ModelAttribute TemplateQueryFrom form){
		Page<TemplateListResult> page = templateService.queryTemplatePage(form);
		return page;
	}
}
