/**
 * 
 */
package com.cc.push.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.push.bean.PageBean;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.TemplateBean;
import com.cc.push.enums.PushStatusEnum;
import com.cc.push.enums.PushTypeEnum;
import com.cc.push.form.PushForm;
import com.cc.push.service.PushService;
import com.cc.push.thread.TemplatePushThread;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/push")
public class PushController {
	
	@Autowired
	private PushService pushService;

	/**
	 * 全部会员推送
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "push" })
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public Response<String> pushAll(@RequestBody PushForm form){
		Response<String> response = new Response<String>();
		if(form.getTemplateId()==null){
			response.setMessage("请选择发送模板");
			return response;
		}
		TemplateBean templateBean = TemplateBean.get(TemplateBean.class, form.getTemplateId());
		if(templateBean==null){
			response.setMessage("发送模板不存在或已删除，请重新选择");
			return response;
		}
		PushBean pushBean = new PushBean();
		pushBean.setTemplateId(templateBean.getTemplateId());
		pushBean.setTitle(templateBean.getTitle());
		if(form.getPageId()!=null){
			PageBean pageBean = PageBean.get(PageBean.class, form.getPageId());
			if(pageBean==null){
				response.setMessage("跳转详情页面不存在或已删除，请重新选择");
				return response;
			}
			pushBean.setPage(pageBean.getPage()+(StringTools.isNullOrNone(form.getParam())?"":("?"+form.getParam())));
		}
		pushBean.setEmphasisKeyword(form.getEmphasisKeyword());
		pushBean.setType(PushStatusEnum.UNDO.getCode());
		pushBean.setType(PushTypeEnum.ALL.getCode());
		pushBean.setCreateTime(DateTools.now());
		try {
			if(form.getKeywords()!=null && !form.getKeywords().isEmpty()){
				pushBean.setContent(JsonTools.toJsonString(form.getKeywords()).getBytes("utf-8"));
			}
			pushService.savePush(pushBean);
			new TemplatePushThread(pushBean.getId()).start();
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
