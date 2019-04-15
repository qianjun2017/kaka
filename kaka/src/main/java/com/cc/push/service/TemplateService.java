/**
 * 
 */
package com.cc.push.service;

import com.cc.common.web.Page;
import com.cc.push.bean.TemplateBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryListResult;

/**
 * @author ws_yu
 *
 */
public interface TemplateService {

	/**
	 * 分页查询个人模板库
	 * @param form
	 * @return
	 */
	Page<TemplateBean> queryTemplatePage(TemplateQueryFrom form);

	/**
	 * 分页查询小程序模板库标题列表
	 * @param form
	 * @return
	 */
	Page<TemplateLibraryListResult> queryTemplateLibraryPage(TemplateLibraryQueryFrom form);
	
	/**
	 * 同步帐号下已存在的模板列表
	 */
	void syncTemplate();

}
