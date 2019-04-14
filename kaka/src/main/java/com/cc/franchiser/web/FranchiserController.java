/**
 * 
 */
package com.cc.franchiser.web;

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
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardBean;
import com.cc.franchiser.bean.FranchiserBean;
import com.cc.franchiser.service.FranchiserService;
import com.cc.franchiser.form.FranchiserQueryForm;
import com.cc.franchiser.result.FranchiserResult;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/franchiser")
public class FranchiserController {
	
	@Autowired
	private FranchiserService franchiserService;

	/**
	 * 删除经销商
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FRANCHISERMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除经销商", paramNames = {"id"})
	public Response<Object> deleteFranchiser(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		FranchiserBean franchiserBean = FranchiserBean.get(FranchiserBean.class, id);
		if(franchiserBean==null){
			response.setMessage("经销商不存在或已删除");
			return response;
		}
		try {
			franchiserService.deleteFranchiser(id);
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
	 * 新增经销商
	 * @param franchiserMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FRANCHISERMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增经销商")
	public Response<Object> addFranchiser(@RequestBody Map<String, Object> franchiserMap){
		Response<Object> response = new Response<Object>();
		FranchiserBean franchiserBean = JsonTools.toObject(JsonTools.toJsonString(franchiserMap), FranchiserBean.class);
		if(StringTools.isNullOrNone(franchiserBean.getName())){
			response.setMessage("请输入经销商名称");
			return response;
		}
		if(franchiserBean.getLocationId()==null){
			response.setMessage("请选择经销商所在地区");
			return response;
		}
		LocationBean locationBean = LocationBean.get(LocationBean.class, franchiserBean.getLocationId());
		if(locationBean==null){
			response.setMessage("经销商所在地区错误，请重新选择");
			return response;
		}
		if(StringTools.isNullOrNone(franchiserBean.getAddress())){
			response.setMessage("请输入经销商详细地址");
			return response;
		}
		try {
			franchiserService.saveFranchiser(franchiserBean);
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
	 * 修改经销商
	 * @param franchiserMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FRANCHISERMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改经销商")
	public Response<Object> updateFranchiser(@RequestBody Map<String, Object> franchiserMap){
		Response<Object> response = new Response<Object>();
		Object id = franchiserMap.get("id");
		if(id==null){
			response.setMessage("缺少经销商主键");
			return response;
		}
		FranchiserBean oldFranchiserBean = CardBean.get(FranchiserBean.class, Long.valueOf(StringTools.toString(id)));
		if(oldFranchiserBean==null){
			response.setMessage("经销商不存在或已删除");
			return response;
		}
		FranchiserBean franchiserBean = JsonTools.toObject(JsonTools.toJsonString(franchiserMap), FranchiserBean.class);
		if(StringTools.isNullOrNone(franchiserBean.getName())){
			response.setMessage("请输入经销商名称");
			return response;
		}
		oldFranchiserBean.setName(franchiserBean.getName());
		if(franchiserBean.getLocationId()==null){
			response.setMessage("请选择经销商所在地区");
			return response;
		}
		LocationBean locationBean = LocationBean.get(LocationBean.class, franchiserBean.getLocationId());
		if(locationBean==null){
			response.setMessage("经销商所在地区错误，请重新选择");
			return response;
		}
		oldFranchiserBean.setLocationId(franchiserBean.getLocationId());
		if(StringTools.isNullOrNone(franchiserBean.getAddress())){
			response.setMessage("请输入经销商详细地址");
			return response;
		}
		oldFranchiserBean.setAddress(franchiserBean.getAddress());
		try {
			franchiserService.saveFranchiser(oldFranchiserBean);
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
	 * 查询经销商详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<FranchiserResult> queryFranchiser(@PathVariable Long id){
		Response<FranchiserResult> response = new Response<FranchiserResult>();
		FranchiserBean franchiserBean = FranchiserBean.get(FranchiserBean.class, id);
		if(franchiserBean==null){
			response.setMessage("经销商不存在或已删除");
			return response;
		}
		FranchiserResult franchiserResult = JsonTools.toObject(JsonTools.toJsonString(franchiserBean), FranchiserResult.class);
		if(franchiserBean.getLocationId()!=null){
			LocationBean locationBean = LocationBean.get(LocationBean.class, franchiserBean.getLocationId());
			if(locationBean!=null && !StringTools.isNullOrNone(locationBean.getLocationNamePath())){
				franchiserResult.setLocationName(locationBean.getLocationNamePath().replace("/", ""));
				Long[] locationArray = new Long[locationBean.getLevel()+1];
				while(locationBean.getParentId()!=null){
					locationArray[locationBean.getLevel()] = locationBean.getId();
					locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
				}
				locationArray[0] = locationBean.getId();
				franchiserResult.setLocationArray(locationArray);
			}
		}
		response.setData(franchiserResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询门店信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<FranchiserResult> queryFranchiserPage(@ModelAttribute FranchiserQueryForm form){
		return franchiserService.queryFranchiserPage(form);
	}
}
