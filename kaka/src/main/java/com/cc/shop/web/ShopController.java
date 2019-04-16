/**
 * 
 */
package com.cc.shop.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardBean;
import com.cc.shop.bean.ShopBean;
import com.cc.shop.form.ShopQueryForm;
import com.cc.shop.result.ShopResult;
import com.cc.shop.service.ShopService;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

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
	public Response<ShopResult> queryShop(@PathVariable Long id){
		Response<ShopResult> response = new Response<ShopResult>();
		ShopBean shopBean = ShopBean.get(ShopBean.class, id);
		if(shopBean==null){
			response.setMessage("门店不存在或已删除");
			return response;
		}
		ShopResult shopResult = JsonTools.toObject(JsonTools.toJsonString(shopBean), ShopResult.class);
		if(shopBean.getLocationId()!=null){
			LocationBean locationBean = LocationBean.get(LocationBean.class, shopBean.getLocationId());
			if(locationBean!=null && !StringTools.isNullOrNone(locationBean.getLocationNamePath())){
				shopResult.setLocationName(locationBean.getLocationNamePath().replace("/", ""));
				Long[] locationArray = new Long[locationBean.getLevel()+1];
				while(locationBean.getParentId()!=null){
					locationArray[locationBean.getLevel()] = locationBean.getId();
					locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
				}
				locationArray[0] = locationBean.getId();
				shopResult.setLocationArray(locationArray);
			}
		}
		response.setData(shopResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 删除门店
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除门店", paramNames = {"id"})
	public Response<Object> deleteShop(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		ShopBean shopBean = ShopBean.get(ShopBean.class, id);
		if(shopBean==null){
			response.setMessage("门店不存在或已删除");
			return response;
		}
		try {
			shopService.deleteShop(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 新增门店
	 * @param shopMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增门店")
	public Response<Object> addShop(@RequestBody Map<String, Object> shopMap){
		Response<Object> response = new Response<Object>();
		ShopBean shopBean = JsonTools.toObject(JsonTools.toJsonString(shopMap), ShopBean.class);
		if(StringTools.isNullOrNone(shopBean.getName())){
			response.setMessage("请输入门店名称");
			return response;
		}
		if(StringTools.isNullOrNone(shopBean.getCode())){
			response.setMessage("请输入门店代码");
			return response;
		}
		List<ShopBean> shopBeanList = ShopBean.findAllByParams(ShopBean.class, "code", shopBean.getCode());
		if(!ListTools.isEmptyOrNull(shopBeanList)){
			response.setMessage("门店代码已存在，请重新输入");
			return response;
		}
		if(StringTools.isAnyNullOrNone(new String[]{shopBean.getLatitude(), shopBean.getLongitude()})){
			response.setMessage("请输入门店经纬度");
			return response;
		}
		if(StringTools.isNullOrNone(shopBean.getRadius())){
			response.setMessage("请输入门店位置误差半径，单位米");
			return response;
		}
		if(shopBean.getLocationId()==null){
			response.setMessage("请选择门店所在地区");
			return response;
		}
		LocationBean locationBean = LocationBean.get(LocationBean.class, shopBean.getLocationId());
		if(locationBean==null){
			response.setMessage("门店所在地区错误，请重新选择");
			return response;
		}
		try {
			shopService.saveShop(shopBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 修改门店
	 * @param shopMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.SHOPMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改门店")
	public Response<Object> updateShop(@RequestBody Map<String, Object> shopMap){
		Response<Object> response = new Response<Object>();
		Object id = shopMap.get("id");
		if(id==null){
			response.setMessage("缺少门店主键");
			return response;
		}
		ShopBean oldShopBean = CardBean.get(ShopBean.class, Long.valueOf(StringTools.toString(id)));
		if(oldShopBean==null){
			response.setMessage("门店不存在或已删除");
			return response;
		}
		ShopBean shopBean = JsonTools.toObject(JsonTools.toJsonString(shopMap), ShopBean.class);
		if(StringTools.isNullOrNone(shopBean.getName())){
			response.setMessage("请输入门店名称");
			return response;
		}
		oldShopBean.setName(shopBean.getName());
		if(StringTools.isNullOrNone(shopBean.getCode())){
			response.setMessage("请输入门店代码");
			return response;
		}
		List<ShopBean> shopBeanList = ShopBean.findAllByParams(ShopBean.class, "code", shopBean.getCode());
		if(!ListTools.isEmptyOrNull(shopBeanList)){
			for(ShopBean codeShopBean: shopBeanList){
				if(codeShopBean.getCode().equals(shopBean.getCode()) && !codeShopBean.getId().equals(shopBean.getId())){
					response.setMessage("门店代码已存在，请重新输入");
					return response;
				}
			}
		}
		oldShopBean.setCode(shopBean.getCode());
		if(StringTools.isAnyNullOrNone(new String[]{shopBean.getLatitude(), shopBean.getLongitude()})){
			response.setMessage("请输入门店经纬度");
			return response;
		}
		oldShopBean.setLatitude(shopBean.getLatitude());
		oldShopBean.setLongitude(shopBean.getLongitude());
		if(StringTools.isNullOrNone(shopBean.getRadius())){
			response.setMessage("请输入门店位置误差半径，单位米");
			return response;
		}
		oldShopBean.setRadius(shopBean.getRadius());
		if(shopBean.getLocationId()==null){
			response.setMessage("请选择门店所在地区");
			return response;
		}
		LocationBean locationBean = LocationBean.get(LocationBean.class, shopBean.getLocationId());
		if(locationBean==null){
			response.setMessage("门店所在地区错误，请重新选择");
			return response;
		}
		oldShopBean.setLocationId(shopBean.getLocationId());
		oldShopBean.setAddress(shopBean.getAddress());
		try {
			shopService.saveShop(oldShopBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
