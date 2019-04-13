/**
 * 
 */
package com.cc.shop.service;

import com.cc.common.web.Page;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.form.ShopQueryForm;

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
	
	/**
	 * 分页查询门店
	 * @param form
	 * @return
	 */
	Page<ShopBean> queryShopPage(ShopQueryForm form);

}
