package com.wicresoft.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wicresoft.erp.web.controller.AbctractBaseController;

/**
 * 首页 Controller
 * @author sunfx
 */
@Controller
public class IndexController extends AbctractBaseController {

	public static final String TILES_INDEX = "tiles_index";

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index.shtml")
	public String index() {
		log4j.info(System.currentTimeMillis());
		return "index";
	}

}
