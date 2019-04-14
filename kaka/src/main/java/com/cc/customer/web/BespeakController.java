/**
 * 
 */
package com.cc.customer.web;

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
import com.cc.consultant.bean.ConsultantBean;
import com.cc.customer.bean.BespeakBean;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.form.BespeakQueryForm;
import com.cc.customer.result.BespeakListResult;
import com.cc.customer.result.BespeakResult;
import com.cc.customer.service.BespeakService;
import com.cc.customer.service.CardService;
import com.cc.franchiser.bean.FranchiserBean;
import com.cc.system.location.bean.LocationBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bespeak")
public class BespeakController {

	@Autowired
	private BespeakService bespeakService;
	
	@Autowired
	private CardService cardService;
	
	/**
	 * 分页查询预约看车
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<BespeakListResult> queryBespeakPage(@ModelAttribute BespeakQueryForm form){
		Page<BespeakListResult> page = bespeakService.queryBespeakPage(form);
		return page;
	}
	
	/**
	 * 查询预约看车详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<BespeakResult> queryBespeak(@PathVariable Long id){
		Response<BespeakResult> response = new Response<BespeakResult>();
		BespeakBean bespeakBean = BespeakBean.get(BespeakBean.class, id);
		if(bespeakBean==null){
			response.setMessage("预约就记录不存在或已删除");
			return response;
		}
		BespeakResult bespeakResult = new BespeakResult();
		bespeakResult.setId(bespeakBean.getId());
		bespeakResult.setBespeakTime(bespeakBean.getBespeakTime());
		bespeakResult.setCreateTime(bespeakBean.getCreateTime());
		if(bespeakBean.getConsultantId()!=null){
			ConsultantBean consultantBean = ConsultantBean.get(ConsultantBean.class, bespeakBean.getConsultantId());
			if(consultantBean!=null){
				bespeakResult.setConsultantName(consultantBean.getName());
				bespeakResult.setConsultantPhone(consultantBean.getPhone());
			}
		}
		if(bespeakBean.getFranchiserId()!=null){
			FranchiserBean franchiserBean = FranchiserBean.get(FranchiserBean.class, bespeakBean.getFranchiserId());
			if(franchiserBean!=null){
				bespeakResult.setFranchiserName(franchiserBean.getName());
				if(franchiserBean.getLocationId()!=null){
					LocationBean locationBean = LocationBean.get(LocationBean.class, franchiserBean.getLocationId());
					if(locationBean!=null && !StringTools.isNullOrNone(locationBean.getLocationNamePath())){
						bespeakResult.setAddress(locationBean.getLocationNamePath().replace("/", "")+franchiserBean.getAddress());
					}
				}
			}
		}
		if(bespeakBean.getCustomerId()!=null){
			CustomerBean customerBean = CustomerBean.get(CustomerBean.class, bespeakBean.getCustomerId());
			bespeakResult.setCustomerName(customerBean.getName());
			bespeakResult.setCustomerPhone(customerBean.getPhone());
			bespeakResult.setPoints(customerBean.getPoints());
			bespeakResult.setCardNo(customerBean.getCardNo());
			CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
			if(cardLevelBean!=null){
				bespeakResult.setLevel(cardLevelBean.getName());
			}
		}
		response.setData(bespeakResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
