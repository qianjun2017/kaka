/**
 * 
 */
package com.cc.push.service;

import com.cc.common.web.Page;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateListResult;

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
	Page<TemplateListResult> queryTemplatePage(TemplateQueryFrom form);

}
