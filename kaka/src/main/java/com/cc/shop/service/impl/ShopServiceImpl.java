/**
 * 
 */
package com.cc.shop.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.service.ShopService;

/**
 * @author Administrator
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveShop(ShopBean shopBean) {
		int row = shopBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存门店失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteShop(Long id) {
		ShopBean shopBean = new ShopBean();
		shopBean.setId(id);
		int row = shopBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除门店失败");
		}
	}

}
