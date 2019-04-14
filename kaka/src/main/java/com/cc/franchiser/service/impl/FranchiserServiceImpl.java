/**
 * 
 */
package com.cc.franchiser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.franchiser.bean.FranchiserBean;
import com.cc.franchiser.dao.FranchiserDao;
import com.cc.franchiser.form.FranchiserQueryForm;
import com.cc.franchiser.result.FranchiserResult;
import com.cc.franchiser.service.FranchiserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class FranchiserServiceImpl implements FranchiserService {

	@Autowired
	private FranchiserDao franchiserDao;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFranchiser(FranchiserBean franchiserBean) {
		int row = franchiserBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存经销商失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteFranchiser(Long id) {
		FranchiserBean franchiserBean = new FranchiserBean();
		franchiserBean.setId(id);
		int row = franchiserBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除经销商失败");
		}
	}

	@Override
	public Page<FranchiserResult> queryFranchiserPage(FranchiserQueryForm form) {
		Page<FranchiserResult> page = new Page<FranchiserResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FranchiserResult> franchiserResultList = franchiserDao.queryFranchiserList(form);
		PageInfo<FranchiserResult> pageInfo = new PageInfo<FranchiserResult>(franchiserResultList);
		if (ListTools.isEmptyOrNull(franchiserResultList)) {
			page.setMessage("没有查询到相关经销商数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(franchiserResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public List<FranchiserResult> queryFranchiserList(FranchiserQueryForm form) {
		return franchiserDao.queryFranchiserList(form);
	}

}
