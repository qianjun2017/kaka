package com.cc.push.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.PushUserBean;
import com.cc.push.dao.FormDao;
import com.cc.push.form.FormQueryForm;
import com.cc.push.result.FormResult;
import com.cc.push.service.PushService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;

@Service
public class PushServiceImpl implements PushService {
	
	@Autowired
	private FormDao formDao;
	
	@Autowired
    private SystemConfigService systemConfigService;

	@Override
	public List<FormResult> queryAllUserFormList() {
		FormQueryForm form = new FormQueryForm();
		SystemConfigBean expriedSystemConfigBean = systemConfigService.querySystemConfigBean("wx.form.expried");
		if(expriedSystemConfigBean==null){
			throw new LogicException("E001", "请设置微信formId有效期(天)");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(expriedSystemConfigBean.getPropertyValue())*-1);
		form.setCreateTimeStart(calendar.getTime());
		return formDao.queryFormList(form);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void savePush(PushBean pushBean) {
		int row = pushBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存推送失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void savePushUser(PushUserBean pushUserBean) {
		int row = pushUserBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存推送人员失败");
		}
	}

}
