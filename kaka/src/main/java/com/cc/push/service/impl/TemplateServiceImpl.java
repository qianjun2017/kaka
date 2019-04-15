/**
 * 
 */
package com.cc.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.push.bean.TemplateBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryListResult;
import com.cc.push.service.TemplateService;
import com.cc.wx.http.request.TemplateLibraryListRequest;
import com.cc.wx.http.response.TemplateLibraryListResponse;
import com.cc.wx.http.response.model.TemplateLibrary;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

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
	public Page<TemplateBean> queryTemplatePage(TemplateQueryFrom form) {
		Page<TemplateBean> page = new Page<TemplateBean>();
		Example example = new Example(TemplateBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getTitle())){
			criteria.andLike("title", "%"+form.getTitle()+"%");
		}
		if(StringTools.isNullOrNone(form.getTemplateId())){
			criteria.andEqualTo("templateId", form.getTemplateId());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<TemplateBean> templateBeanList = TemplateBean.findByExample(TemplateBean.class, example);
		PageInfo<TemplateBean> pageInfo = new PageInfo<TemplateBean>(templateBeanList);
		if (ListTools.isEmptyOrNull(templateBeanList)) {
			page.setMessage("没有查询到相关模板数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(templateBeanList);
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
