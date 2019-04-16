/**
 * 
 */
package com.cc.push.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.push.bean.TemplateBean;
import com.cc.push.bean.TemplateKeywordBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateKeywordResult;
import com.cc.push.result.TemplateLibraryListResult;
import com.cc.push.result.TemplateLibraryResult;
import com.cc.push.result.TemplateResult;
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
	
	/**
	 * 获取帐号下已存在的模板详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<TemplateResult> queryTemplate(@PathVariable Long id){
		Response<TemplateResult> response = new Response<TemplateResult>();
		TemplateBean templateBean = TemplateBean.get(TemplateBean.class, id);
		if(templateBean==null){
			response.setMessage("消息模板不存在或已删除");
			return response;
		}
		TemplateResult templateResult = JsonTools.covertObject(templateBean, TemplateResult.class);
		List<TemplateKeywordBean> templateKeywordBeanList = TemplateKeywordBean.findAllByParams(TemplateKeywordBean.class, "templateId", templateBean.getId());
		if(!ListTools.isEmptyOrNull(templateKeywordBeanList)){
			List<TemplateKeywordResult> keywordList = new ArrayList<TemplateKeywordResult>();
			for(TemplateKeywordBean templateKeywordBean: templateKeywordBeanList){
				keywordList.add(JsonTools.covertObject(templateKeywordBean, TemplateKeywordResult.class));
			}
			templateResult.setKeywordList(keywordList);
		}
		response.setData(templateResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 获取模板库模板详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/library/get/{id}", method = RequestMethod.GET)
	public Response<TemplateLibraryResult> queryTemplateLibrary(@PathVariable String id){
		Response<TemplateLibraryResult> response = new Response<TemplateLibraryResult>();
		try {
			TemplateLibraryResult templateLibraryResult = templateService.queryTemplateLibrary(id);
			response.setData(templateLibraryResult);
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
