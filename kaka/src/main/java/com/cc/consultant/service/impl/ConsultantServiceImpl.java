/**
 * 
 */
package com.cc.consultant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.consultant.service.ConsultantService;
import com.cc.consultant.bean.ConsultantBean;
import com.cc.consultant.form.ConsultantQueryForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class ConsultantServiceImpl implements ConsultantService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveConsultant(ConsultantBean consultantBean) {
		int row = consultantBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存汽车顾问失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteConsultant(Long id) {
		ConsultantBean consultantBean = new ConsultantBean();
		consultantBean.setId(id);
		int row = consultantBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除汽车顾问失败");
		}
	}

	@Override
	public Page<ConsultantBean> queryConsultantPage(ConsultantQueryForm form) {
		Page<ConsultantBean> page = new Page<ConsultantBean>();
		Example example = new Example(ConsultantBean.class);
		Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getName())){
			criteria.andEqualTo("name", form.getName());
		}
		if(!StringTools.isNullOrNone(form.getPhone())){
			criteria.andEqualTo("phone", form.getPhone());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ConsultantBean> consultantBeanList = ConsultantBean.findByExample(ConsultantBean.class, example);
		PageInfo<ConsultantBean> pageInfo = new PageInfo<ConsultantBean>(consultantBeanList);
		if (ListTools.isEmptyOrNull(consultantBeanList)) {
			page.setMessage("没有查询到相关汽车顾问数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(consultantBeanList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
