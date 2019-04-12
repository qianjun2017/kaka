/**
 * 
 */
package com.cc.customer.service;

import com.cc.customer.bean.CardBean;
import com.cc.customer.bean.CardLevelBean;

/**
 * @author Administrator
 *
 */
public interface CardService {

	/**
	 * 查询积分所属会员等级
	 * @param points
	 * @return
	 */
	CardLevelBean queryCardLevelByPoints(Long points);

	/**
	 * 保存会员卡
	 * @param cardBean
	 */
	void saveCard(CardBean cardBean);

	/**
	 * 保存会员卡级别
	 * @param cardLevelBean
	 */
	void saveCardLevel(CardLevelBean cardLevelBean);
}
