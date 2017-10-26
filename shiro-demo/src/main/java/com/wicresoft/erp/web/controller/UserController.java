package com.wicresoft.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wicresoft.erp.web.controller.base.AbctractBaseController;

/**
 * 用户 Controller
 * 
 * @author sunfx
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbctractBaseController {

	private final static String userAddJSP = "user/userAdd";
	private final static String userListJSP = "user/userList";

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public String list() {
		log4j.info(System.currentTimeMillis());
		return userListJSP;
	}
	
	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/add.shtml")
	public String add() {
		log4j.info(System.currentTimeMillis());
		return userAddJSP;
	}

}
