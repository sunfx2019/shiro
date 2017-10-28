package com.wicresoft.erp.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wicresoft.erp.entity.TUser;
import com.wicresoft.erp.service.IUserService;
import com.wicresoft.erp.web.controller.base.AbctractBaseController;

/**
 * 用户 Controller
 * 
 * @author sunfx
 */
@Controller
@RequestMapping("/user")
public class UserController extends AbctractBaseController {

	private final static String userListJSP = "user/userList";
	private final static String userAddJSP = "user/userAdd";
	private final static String userUpdateJSP = "user/userUpdate";
	private final static String userDeleteJSP = "user/userDelete";
	
	@Autowired
	private IUserService userService;

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/list.shtml")
	public String list(Integer pageNo, Integer pageSize) {
		pageNo = (pageNo == null ? 1 : pageNo); // 当前页 默认为1
		pageSize = (pageSize == null ? 10 : pageSize); // 每页大小 默认10		
		PageHelper.startPage(pageNo, pageSize); // 分页工具
		List<TUser> userList = userService.fiandAll();
		PageInfo<TUser> page = new PageInfo<TUser>(userList);
        model.addAttribute("userList", userList);
        model.addAttribute("page", page);
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
	
	@RequestMapping("/update.shtml")
	public String update() {
		log4j.info(System.currentTimeMillis());
		return userUpdateJSP;
	}
	
	@RequestMapping("/delete.shtml")
	public String delete() {
		log4j.info(System.currentTimeMillis());
		return userDeleteJSP;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
