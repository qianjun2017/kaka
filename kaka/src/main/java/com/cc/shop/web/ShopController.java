/**
 * 
 */
package com.cc.shop.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.service.ShopService;
import com.cc.system.location.bean.LocationBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/shop")
public class ShopController {
	
	@Autowired
	private ShopService shopService;

	/**
	 * 分页查询门店信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<ShopBean> queryShopPage(@ModelAttribute ShopQueryForm form){
		return shopService.queryShopPage(form);
	}
	
	/**
	 * 查询门店详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ShopBean> queryShop(@PathVariable Long id){
		Response<ShopBean> response = new Response<ShopBean>();
		ShopBean shopBean = ShopBean.get(ShopBean.class, id);
		if(shopBean==null){
			response.setMessage("门店不存在或已删除");
		}
		if(shopBean.getLocationId()!=null){
			LocationBean locationBean = LocationBean.get(LocationBean.class, shopBean.getLocationId());
			if(locationBean!=null && !StringTools.isNullOrNone(locationBean.getLocationNamePath())){
				String address = locationBean.getLocationNamePath().replace("/", "")+shopBean.getAddress();
				shopBean.setAddress(address);
			}
		}
		return response;
	}
}
