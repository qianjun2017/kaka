/**
 * 
 */
package com.cc.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.customer.form.BespeakQueryForm;
import com.cc.customer.result.BespeakListResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface BespeakDao extends CrudDao {

	/**
	 * 查询预约看车记录
	 * @param form
	 * @return
	 */
	List<BespeakListResult> queryBespeakList(BespeakQueryForm form);
}
