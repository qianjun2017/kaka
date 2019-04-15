/**
 * 
 */
package com.cc.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.ScanForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.DistanceTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.service.CardService;
import com.cc.customer.service.PointsService;
import com.cc.shop.bean.ShopBean;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/points")
public class ApiPointsController {

	@Autowired
	private PointsService pointsService;
	
	@Autowired
	private CardService cardService;
	
	/**
	 * 扫描积分二维码
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	public Response<Object> scanAcode(@RequestBody ScanForm form) {
		Response<Object> response = new Response<Object>();
		if(form.getLatitude()==null || form.getLongitude()==null){
			response.setMessage("请输入经纬度");
			return response;
		}
		if(form.getPoints()==null){
			response.setMessage("请输入变更积分数");
			return response;
		}
		if(form.getShopId()==null){
			response.setMessage("请输入积分码所属门店");
			return response;
		}
		ShopBean shopBean = ShopBean.get(ShopBean.class, form.getShopId());
		if(shopBean==null){
			response.setMessage("积分码所属门店不存在或已删除");
			return response;
		}
		if(StringTools.isAnyNullOrNone(new String[]{shopBean.getLatitude(), shopBean.getLongitude(), shopBean.getRadius()})){
			response.setMessage("积分码参数缺失");
			return response;
		}
		Long customerId = RequestContextUtil.getCustomerId();
		Date now = DateTools.now();
		if(form.getPoints()>0){
			Example example = new Example(PointsBean.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("customerId", customerId);
			criteria.andEqualTo("shopId", form.getShopId());
			Date startTime = DateTools.getDate((DateTools.getFormatDate(now, DateTools.DATEFORMAT3) + " 00:00:00"));
			Date endTime = DateTools.getDate((DateTools.getFormatDate(now, DateTools.DATEFORMAT3) + " 23:59:59"));
			criteria.andGreaterThanOrEqualTo("createTime", startTime);
			criteria.andLessThanOrEqualTo("createTime", endTime);
			List<PointsBean> pointsBeanList = PointsBean.findByExample(PointsBean.class, example);
			if(!ListTools.isEmptyOrNull(pointsBeanList)){
				response.setMessage("今日在"+shopBean.getName()+"加分码已达达上限，请明日再试");
				return response;
			}
		}
		Double distance = DistanceTools.getDistanceBetween(form.getLongitude(), form.getLatitude(), Double.valueOf(shopBean.getLongitude()), Double.valueOf(shopBean.getLatitude()));
		if(distance.compareTo(Double.valueOf(shopBean.getRadius()))>0){
			response.setMessage("请在"+shopBean.getName()+"附近"+shopBean.getRadius()+"米范围内扫码");
			return response;
		}
		PointsBean pointsBean = new PointsBean();
		pointsBean.setCreateTime(now);
		pointsBean.setCustomerId(customerId);
		pointsBean.setPoints(form.getPoints());
		pointsBean.setShopId(form.getShopId());
		pointsBean.setRemark("扫"+shopBean.getName()+(form.getPoints()>0?"加":"减")+"分码");
		try {
			pointsService.savePoints(pointsBean);
			CustomerBean customerBean = CustomerBean.get(CustomerBean.class, customerId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(customerBean!=null){
				dataMap.put("points", customerBean.getPoints());
				CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
				if(cardLevelBean!=null){
					dataMap.put("cardLevel", cardLevelBean.getName());
					dataMap.put("cardImage", cardLevelBean.getImageUrl());
				}
			}
			response.setData(dataMap);
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
