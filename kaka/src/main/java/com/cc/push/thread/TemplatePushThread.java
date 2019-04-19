/**
 * 
 */
package com.cc.push.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.spring.SpringContextUtil;
import com.cc.common.tools.ListTools;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.PushUserBean;
import com.cc.push.enums.PushStatusEnum;
import com.cc.push.enums.PushTypeEnum;
import com.cc.push.result.FormResult;
import com.cc.push.service.PushService;
import com.cc.wx.http.request.TemplateMessageRequest;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;

/**
 * @author ws_yu
 *
 */
public class TemplatePushThread extends Thread {
	
	private Long pushId;

	public TemplatePushThread(Long pushId) {
		this.pushId = pushId;
	}

	@Override
	public void run() {
		PushBean pushBean = PushBean.get(PushBean.class, pushId);
		if(pushBean==null){
			return ;
		}
		List<PushUserBean> pushUserBeanList = new ArrayList<PushUserBean>();
		PushTypeEnum pushTypeEnum = PushTypeEnum.getPushTypeEnumByCode(pushBean.getType());
		if(PushTypeEnum.ASSIGN.equals(pushTypeEnum)){
			pushUserBeanList.addAll(PushUserBean.findAllByParams(PushUserBean.class, "pushId", pushBean.getId()));
		}else if(PushTypeEnum.ALL.equals(pushTypeEnum)){
			PushService pushService = SpringContextUtil.getBean(PushService.class);
			List<FormResult> userFormList = pushService.queryAllUserFormList();
			if(!ListTools.isEmptyOrNull(userFormList)){
				pushUserBeanList.addAll(userFormList.stream().map(userForm->{
					PushUserBean pushUserBean = new PushUserBean();
					pushUserBean.setPushId(pushBean.getId());
					pushUserBean.setOpenid(userForm.getOpenid());
					pushUserBean.setName(userForm.getUserName());
					return pushUserBean;
				}).collect(Collectors.toList()));
			}
		}
		if(ListTools.isEmptyOrNull(pushUserBeanList)){
			pushBean.setStatus(PushStatusEnum.DONE.getCode());
			pushBean.save();
			return;
		}
		pushBean.setStatus(PushStatusEnum.DOING.getCode());
		pushBean.save();
		WeiXinService weiXinService = SpringContextUtil.getBean(WeiXinService.class);
		AccessTokenService accessTokenService = SpringContextUtil.getBean(AccessTokenService.class);
		TemplateMessageRequest request = new TemplateMessageRequest();
		request.setAccessToken(accessTokenService.queryAccessToken());
		request.setPage(pushBean.getPage());
		request.setEmphasisKeyword(pushBean.getEmphasisKeyword());
		request.setTemplateId(pushBean.getTemplateId());
		for(PushUserBean pushUserBean: pushUserBeanList){
			request.setToUser(pushUserBean.getOpenid());
			weiXinService.sendTemplateMessage(request);
		}
	}

}
