package com.cc.franchiser.service;

import java.util.List;

import com.cc.common.web.Page;
import com.cc.franchiser.bean.FranchiserBean;
import com.cc.franchiser.form.FranchiserQueryForm;
import com.cc.franchiser.result.FranchiserResult;

public interface FranchiserService {

	/**
	 * 保存经销商
	 * @param franchiserBean
	 */
	void saveFranchiser(FranchiserBean franchiserBean);
	
	/**
	 * 删除经销商
	 * @param id
	 */
	void deleteFranchiser(Long id);

	/**
	 * 查询经销商
	 * @param form
	 * @return
	 */
	Page<FranchiserResult> queryFranchiserPage(FranchiserQueryForm form);
	
	/**
	 * 查询经销商
	 * @param form
	 * @return
	 */
	List<FranchiserResult> queryFranchiserList(FranchiserQueryForm form);
}
