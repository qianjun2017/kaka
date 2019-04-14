/**
 * 
 */
package com.cc.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.carousel.enums.CarouselStatusEnum;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.carousel.service.CarouselService;
import com.cc.common.web.Page;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/carousel")
public class ApCarouselController {

	@Autowired
	private CarouselService carouselService;
	
	/**
	 * 分页查询轮播图
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Page<Map<String, Object>> queryCarouselPage(@ModelAttribute CarouselQueryForm form){
		form.setStatus(CarouselStatusEnum.ON.getName());
		return carouselService.queryCarouselPage(form);
	}
}
