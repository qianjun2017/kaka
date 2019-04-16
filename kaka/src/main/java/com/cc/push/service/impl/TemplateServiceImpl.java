/**
 * 
 */
package com.cc.push.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.push.bean.TemplateBean;
import com.cc.push.bean.TemplateKeywordBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryListResult;
import com.cc.push.result.TemplateLibraryResult;
import com.cc.push.service.TemplateService;
import com.cc.wx.http.request.AddTemplateRequest;
import com.cc.wx.http.request.TemplateLibraryListRequest;
import com.cc.wx.http.request.TemplateLibraryRequest;
import com.cc.wx.http.request.TemplateListRequest;
import com.cc.wx.http.response.AddTemplateResponse;
import com.cc.wx.http.response.TemplateLibraryListResponse;
import com.cc.wx.http.response.TemplateLibraryResponse;
import com.cc.wx.http.response.TemplateListResponse;
import com.cc.wx.http.response.model.Template;
import com.cc.wx.http.response.model.TemplateLibrary;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author ws_yu
 *
 */
@Service
public class TemplateServiceImpl implements TemplateService {
	
	private static Logger logger = LoggerFactory.getLogger(TemplateService.class);
	
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

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void syncTemplate() {
		while(true){
			int offset = 0;
			TemplateListRequest request = new TemplateListRequest();
			request.setOffset(offset);
			request.setCount(20);
			request.setAccessToken(accessTokenService.queryAccessToken());
			TemplateListResponse response = WeiXinService.queryTemplateList(request);
			if(!response.isSuccess()){
				logger.warn("消息模板获取失败------"+response.getMessage()+"--------");
				return;
			}
			List<Template> templateList = response.getList();
			if(ListTools.isEmptyOrNull(templateList)){
				return;
			}
			for(Template template: templateList){
				TemplateBean templateBean;
				List<TemplateBean> templateBeanList = TemplateBean.findAllByParams(TemplateBean.class, "templateId", template.getId());
				if(ListTools.isEmptyOrNull(templateBeanList)){
					templateBean = new TemplateBean();
				}else{
					templateBean = templateBeanList.get(0);
				}
				templateBean.setTemplateId(template.getId());
				templateBean.setTitle(template.getTitle());
				templateBean.setCreateTime(DateTools.now());
				int row = templateBean.save();
				if(row!=1){
					logger.warn("消息模板:"+templateBean.getTitle()+",保存失败");
					continue;
				}
				Example templateKeywordExample = new Example(TemplateKeywordBean.class);
				Criteria criteria = templateKeywordExample.createCriteria();
				criteria.andEqualTo("templateId", templateBean.getId());
				TemplateKeywordBean.deleteByExample(TemplateKeywordBean.class, templateKeywordExample);
				String content = template.getContent();
				String example = template.getExample();
				if(StringTools.isAnyNullOrNone(new String[]{content, example})){
					logger.warn("消息模板:"+templateBean.getTitle()+",模板内容:"+content+",模板内容示例:"+example+",不匹配");
					continue;
				}
				String[] contents = content.split("\n");
				String[] examples = example.split("\n");
				if(contents.length!=examples.length){
					logger.warn("消息模板:"+templateBean.getTitle()+",模板内容:"+content+",模板内容示例:"+example+",不匹配");
					continue;
				}
				for(int index=0; index<contents.length; index++){
					if(StringTools.isNullOrNone(contents[index])){
						logger.warn("消息模板:"+templateBean.getTitle()+",模板内容:"+content+",关键字名称为空");
						continue;
					}
					if(StringTools.isNullOrNone(examples[index])){
						logger.warn("消息模板:"+templateBean.getTitle()+",模板内容示例:"+example+",关键字示例为空");
						continue;
					}
					TemplateKeywordBean templateKeywordBean = new TemplateKeywordBean();
					templateKeywordBean.setTemplateId(templateBean.getId());
					String[] ne = examples[index].split("：");
					if(ne==null || ne.length!=2){
						logger.warn("消息模板:"+templateBean.getTitle()+",模板内容示例:"+examples[index]+",格式错误");
						continue;
					}
					templateKeywordBean.setName(ne[0]);
					templateKeywordBean.setExample(ne[1]);
					templateKeywordBean.setKeyword("keyword"+(index+1));
					templateKeywordBean.setKeydata(templateKeywordBean.getKeyword()+".DATA");
					row = templateKeywordBean.save();
					if(row!=1){
						logger.warn("消息模板:"+templateBean.getTitle()+",关键字名称:"+templateKeywordBean.getName()+",保存失败");
						continue;
					}
				}
			}
			if(templateList.size()<20){
				return;
			}
			offset += 20;
		}
	}

	@Override
	public TemplateLibraryResult queryTemplateLibrary(String id) {
		TemplateLibraryRequest request = new TemplateLibraryRequest();
		request.setAccessToken(accessTokenService.queryAccessToken());
		request.setId(id);
		TemplateLibraryResponse response = WeiXinService.queryTemplateLibrary(request);
		if(!response.isSuccess()){
			throw new LogicException("E001", response.getMessage());
		}
		TemplateLibraryResult templateLibraryResult = JsonTools.covertObject(response, TemplateLibraryResult.class);
		return templateLibraryResult;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void addTemplate(String id, List<Long> keywordIdList) {
		AddTemplateRequest request = new AddTemplateRequest();
		request.setAccessToken(accessTokenService.queryAccessToken());
		request.setId(id);
		request.setKeywordIdList(keywordIdList.toArray(new Long[]{}));
		AddTemplateResponse response = WeiXinService.addTemplate(request);
		if(!response.isSuccess()){
			throw new LogicException("E001", response.getMessage());
		}
		syncTemplate();
	}

}
