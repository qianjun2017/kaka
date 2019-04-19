/**
 * 
 */
package com.cc.push.service;

import java.util.List;

import com.cc.push.bean.FormBean;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.PushUserBean;
import com.cc.push.result.FormResult;

/**
 * @author Administrator
 *
 */
public interface PushService {

	/**
	 * 查询所有用户的Form，每个用户查询出最近到期的一条数据
	 * @return
	 */
	List<FormResult> queryAllUserFormList();
	
	/**
	 * 保存推送信息
	 * @param pushBean
	 */
	void savePush(PushBean pushBean);
	
	/**
	 * 保存推送人员信息
	 * @param pushUserBean
	 */
	void savePushUser(PushUserBean pushUserBean);
	
	/**
	 * 查询用户最近到期的一条数据
	 * @param userId
	 * @return
	 */
	FormBean queryUserForm(Long userId);
	
}
