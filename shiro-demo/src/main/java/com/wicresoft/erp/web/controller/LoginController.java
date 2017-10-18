package com.wicresoft.erp.web.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wicresoft.erp.bean.User;
import com.wicresoft.erp.web.controller.AbctractBaseController;

/**
 * 首页 Controller
 * 
 * @author sunfx
 */
@Controller
public class LoginController extends AbctractBaseController {

	public static final String TILES_INDEX = "tiles_index";

	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value = "/login.shtml")
	public String login() {
		return "login";
	}

	/**
	 * 退出URL
	 * @return
	 */
	@RequestMapping(value = "/loginOut.shtml", method = RequestMethod.POST)
	public String loginOut() {
		this.session.invalidate();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}

	/**
	 * 登陆验证
	 * 
	 * @param user
	 * @param bindingResult
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/loginCheck.shtml", method = RequestMethod.POST)
	public String loginCheck(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		try {
			if (bindingResult.hasErrors()) {
				return "/login";
			}
			// 使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！
			SecurityUtils.getSubject().login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
			return "redirect:/index.shtml";
		} catch (AuthenticationException e) {
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误");
			return "redirect:/login.shtml";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", "登陆异常！");
			return "redirect:/login.shtml";
		}
	}

}
