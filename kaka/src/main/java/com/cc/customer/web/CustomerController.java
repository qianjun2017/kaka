/**
 * 
 */
package com.cc.customer.web;


import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.service.CardService;
import com.cc.customer.service.CustomerService;
import com.cc.customer.service.PointsService;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.bean.PointsBean;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.form.PointsQueryForm;
import com.cc.customer.result.CustomerListResult;
import com.cc.customer.result.CustomerResult;
import com.cc.customer.result.PointsListResult;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private PointsService pointsService;
	
	/**
	 * 查询会员信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.detail" })
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<CustomerResult> queryCustomer(@PathVariable Long id){
		Response<CustomerResult> response = new Response<CustomerResult>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		CustomerResult customerResult = JsonTools.toObject(JsonTools.toJsonString(customerBean), CustomerResult.class);
		CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
		if(cardLevelBean!=null){
			customerResult.setCardLevel(cardLevelBean.getName());
			customerResult.setCardImage(cardLevelBean.getImageUrl());
			customerResult.setCardColor(cardLevelBean.getColor());
		}
		response.setData(customerResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 锁定会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.lock" })
	@RequestMapping(value = "/lock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.LOCK, title = "锁定会员", paramNames = {"id"})
	public Response<String> lockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		try {
			customerService.lockCustomer(id);
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
	 * 解锁会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.unlock" })
	@RequestMapping(value = "/unlock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.UNLOCK, title = "解锁会员", paramNames = {"id"})
	public Response<String> unlockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("会员不存在或已删除");
			return response;
		}
		try {
			customerService.unLockCustomer(id);
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
	 * 分页查询会员信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer" })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<CustomerListResult> queryCustomerPage(@ModelAttribute CustomerQueryForm form){
		Page<CustomerListResult> page = customerService.queryCustomerPage(form);
		return page;
	}

	/**
	 * 调减会员积分
	 * @param pointsMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.points" })
	@RequestMapping(value = "/points", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.POINTSMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "调减会员积分")
	public Response<Object> addCard(@RequestBody Map<String, Object> pointsMap){
		Response<Object> response = new Response<Object>();
		UserBean user = SecurityContextUtil.getCurrentUser();
		if(user==null){
			response.setMessage("请先登录");
			return response;
		}
		Object customerId = pointsMap.get("customerId");
		if(customerId==null){
			response.setMessage("请选择会员");
			return response;
		}
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, Long.valueOf(String.valueOf(customerId)));
		if(customerBean==null){
			response.setMessage("会员不存在或已删除");
			return response;
		}
		Object points = pointsMap.get("points");
		if(points==null){
			response.setMessage("请输入变更积分数");
			return response;
		}
		Object remark = pointsMap.get("remark");
		if(remark==null){
			response.setMessage("请输入变更积分原因");
			return response;
		}
		PointsBean pointsBean = new PointsBean();
		pointsBean.setCustomerId(customerBean.getId());
		pointsBean.setCreateTime(DateTools.now());
		pointsBean.setPoints(Long.valueOf(String.valueOf(points)));
		pointsBean.setUserId(user.getId());
		pointsBean.setRemark(StringTools.toString(remark));
		try {
			pointsService.savePoints(pointsBean);
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
	 * 分页查询会员积分变更
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.points" })
	@RequestMapping(value = "/points/page", method = RequestMethod.GET)
	public Page<PointsListResult> queryCustomerPage(@ModelAttribute PointsQueryForm form){
		Page<PointsListResult> page = pointsService.queryPointsPage(form);
		return page;
	}
}
