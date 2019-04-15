/**
 * 
 */
package com.cc.wx.service;

import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.request.TemplateListRequest;
import com.cc.wx.http.request.TemplateMessageRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.http.response.OpenidResponse;
import com.cc.wx.http.response.TemplateListResponse;
import com.cc.wx.http.response.TemplateMessageResponse;
import com.cc.wx.http.response.WXACodeResponse;

/**
 * @author Administrator
 *
 */
public interface WeiXinService {

	/**
	 * 获取用户对应的openid
	 * @param request
	 * @return
	 */
	OpenidResponse queryOpenid(OpenidRequest request);
	
	/**
	 * 获取小程序调用凭证
	 * @param request
	 * @return
	 */
	AccessTokenResponse queryAccessToken(AccessTokenRequest request);
	
	/**
	 * 创建小程序二维码
	 * @param request
	 * @return
	 */
	WXACodeResponse createWXACode(WXACodeRequest request);
	
	/**
	 * 发送模板消息
	 * @param request
	 * @return
	 */
	TemplateMessageResponse sendTemplateMessage(TemplateMessageRequest request);
	
	/**
	 * 获取帐号下已存在的模板列表
	 * @param request
	 * @return
	 */
	TemplateListResponse queryTemplateList(TemplateListRequest request);
	
}
