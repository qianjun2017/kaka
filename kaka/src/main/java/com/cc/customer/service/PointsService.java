/**
 * 
 */
package com.cc.customer.service;

import com.cc.customer.bean.PointsBean;

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
}
