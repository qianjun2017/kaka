/**
 * 
 */
package com.cc.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.form.ShopQueryForm;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ShopDao extends CrudDao {

	/**
	 * 查询门店
	 * @param form
	 * @return
	 */
	List<ShopBean> queryShopList(ShopQueryForm form);
}
