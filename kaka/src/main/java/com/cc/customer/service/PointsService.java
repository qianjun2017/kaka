/**
 * 
 */
package com.cc.customer.service;

import com.cc.common.web.Page;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.form.PointsQueryForm;
import com.cc.customer.result.PointsListResult;

/**
 * @author Administrator
 *
 */
public interface PointsService {

	/**
	 * 保存积分变更
	 * @param pointsBean
	 */
	void savePoints(PointsBean pointsBean);

	/**
	 * 分页查询积分变更
	 * @param form
	 * @return
	 */
	Page<PointsListResult> queryPointsPage(PointsQueryForm form);
}
