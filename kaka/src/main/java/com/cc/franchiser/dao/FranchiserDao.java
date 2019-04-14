/**
 * 
 */
package com.cc.franchiser.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.franchiser.form.FranchiserQueryForm;
import com.cc.franchiser.result.FranchiserResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface FranchiserDao extends CrudDao {

	/**
	 * 查询经销商列表
	 * @param form
	 * @return
	 */
	List<FranchiserResult> queryFranchiserList(FranchiserQueryForm form);

}
