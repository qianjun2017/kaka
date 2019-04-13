/**
 * 
 */
package com.cc.shop.service;

import com.cc.shop.bean.ShopBean;

/**
 * @author Administrator
 *
 */
public interface ShopService {
	
	/**
	 * 保存门店信息
	 * @param shopBean
	 */
	void saveShop(ShopBean shopBean);
	
	/**
	 * 删除指定门店
	 * @param id
	 */
	void deleteShop(Long id);

}
