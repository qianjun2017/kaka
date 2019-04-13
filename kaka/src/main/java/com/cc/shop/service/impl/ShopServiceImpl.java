/**
 * 
 */
package com.cc.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.dao.ShopDao;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.service.ShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
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

	@Override
	public Page<ShopBean> queryShopPage(ShopQueryForm form) {
		Page<ShopBean> page = new Page<ShopBean>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ShopBean> shopBeanList = shopDao.queryShopList(form);
		PageInfo<ShopBean> pageInfo = new PageInfo<ShopBean>(shopBeanList);
		if (ListTools.isEmptyOrNull(shopBeanList)) {
			page.setMessage("没有查询到相关门店数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(shopBeanList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
