/**
 * 
 */
package com.cc.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryListResult;
import com.cc.push.result.TemplateListResult;
import com.cc.push.service.TemplateService;
import com.cc.wx.http.request.TemplateLibraryListRequest;
import com.cc.wx.http.request.TemplateListRequest;
import com.cc.wx.http.response.TemplateLibraryListResponse;
import com.cc.wx.http.response.TemplateListResponse;
import com.cc.wx.http.response.model.Template;
import com.cc.wx.http.response.model.TemplateLibrary;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;

/**
 * @author ws_yu
 *
 */
@Service
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	private WeiXinService WeiXinService;
	
	@Autowired
    private AccessTokenService accessTokenService;

	@Override
	public Page<TemplateListResult> queryTemplatePage(TemplateQueryFrom form) {
		Page<TemplateListResult> page = new Page<TemplateListResult>();
		TemplateListRequest request = new TemplateListRequest();
		request.setOffset((form.getPage()-1)*form.getPageSize());
		request.setCount(form.getPageSize());
		request.setAccessToken(accessTokenService.queryAccessToken());
		TemplateListResponse response = WeiXinService.queryTemplateList(request);
		if(!response.isSuccess()){
			page.setMessage(response.getMessage());
			return page;
		}
		List<Template> list = response.getList();
		if(ListTools.isEmptyOrNull(list)){
			page.setMessage("没有查询到相关个人模板库数据");
			return page;
		}
		List<TemplateListResult> templateList = new ArrayList<TemplateListResult>();
		for(Template template: list){
			TemplateListResult templateListResult = new TemplateListResult();
			templateListResult.setId(template.getId());
			templateListResult.setTitle(template.getTitle());
			templateListResult.setContent(template.getContent());
			templateListResult.setExample(template.getExample());
			templateList.add(templateListResult);
		}
		page.setPage(form.getPage());
		page.setPageSize(form.getPageSize());
		page.setData(templateList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public Page<TemplateLibraryListResult> queryTemplateLibraryPage(TemplateLibraryQueryFrom form) {
		Page<TemplateLibraryListResult> page = new Page<TemplateLibraryListResult>();
		TemplateLibraryListRequest request = new TemplateLibraryListRequest();
		request.setOffset((form.getPage()-1)*form.getPageSize());
		request.setCount(form.getPageSize());
		request.setAccessToken(accessTokenService.queryAccessToken());
		TemplateLibraryListResponse response = WeiXinService.queryTemplateLibraryList(request);
		if(!response.isSuccess()){
			page.setMessage(response.getMessage());
			return page;
		}
		List<TemplateLibrary> list = response.getList();
		if(ListTools.isEmptyOrNull(list)){
			page.setMessage("没有查询到相关模板库数据");
			return page;
		}
		List<TemplateLibraryListResult> templateLibraryList = new ArrayList<TemplateLibraryListResult>();
		for(TemplateLibrary templateLibrary: list){
			TemplateLibraryListResult templateLibraryListResult = new TemplateLibraryListResult();
			templateLibraryListResult.setId(templateLibrary.getId());
			templateLibraryListResult.setTitle(templateLibrary.getTitle());
			templateLibraryList.add(templateLibraryListResult);
		}
		page.setPage(form.getPage());
		page.setPageSize(form.getPageSize());
		page.setTotal(response.getTotal());
		page.setData(templateLibraryList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
