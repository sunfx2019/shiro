package com.wicresoft.erp.web.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.wicresoft.erp.core.util.ApplicationContextFactoryUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 抽象的 Base Controller
 * 
 * @author sunfx
 * 
 */
public abstract class AbctractBaseController {

	protected Model model;

	protected HttpSession session;

	protected HttpServletRequest request;

	protected HttpServletResponse response;

	protected final Log log4j = LogFactory.getLog(getClass());

	@Autowired
	protected ApplicationContextFactoryUtil springContextUtil;

	//当前页
	public static final String PAGENO_KEY = "pageNo";
	
	//每页大小
	public static final String PAGESIZE_KEY = "pageSize";
	
	//分页对象 Session Key
	public static final String PAGE_KEY = "page";
	
	// 请求返回消息KEY
	public static final String MESSAGE_KEY = "message_key";
	
	// 请求返回结果KEY
	public static final String RESULT_KEY = "result_key";

	// 重定向前缀
	public static final String REDIRECT_PREFIX = "redirect:";

	// 系统用户 KEY（页面Session中取值：${session.SPRING_SECURITY_CONTEXT)）
	public static final String USER_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

	// WEB-INF/view/jsp/message.jsp
	public static final String error_message_jsp = "message";

	/**
	 * spring 自动注入 请求、响应、参数对象
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	private void setReqAndRes(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.model = model;
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}

	/**
	 * 获取保存在Session中的用户对象
	 * 
	 * 页面中取值：${session.SPRING_SECURITY_CONTEXT)
	 * 
	 * @param request
	 * @return
	 */
//	protected UserLoginDetails getSessionUser() {
//		UserLoginDetails userDetails = null;
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		if (securityContext != null && securityContext.getAuthentication() != null && securityContext.getAuthentication().getPrincipal() != null) {
//			userDetails = (UserLoginDetails) securityContext.getAuthentication().getPrincipal();
//		}
//		return userDetails;
//	}
	
//	protected BigDecimal getSessionUserId() {
//		return this.getSessionUser().getId();
//	}

	/**
	 * 获取请求属性封装为Map类型
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected HashMap<String, Object> getRequestParameters() {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		for (Object o : map.keySet()) {
			String key = (String) o;
			conditions.put(key, ((String[]) map.get(key))[0]);
		}
		return conditions;
	}
	
	/**
	 * 获取请求参数
	 * 
	 * @param name
	 * @return
	 */
	protected String getRequestParameterByName(String name) {
		return this.request.getParameter(name);
	}
	
	/**
	 * 设置Session请求参数
	 * @param paramertValue
	 */
	protected void setSessionAttribute(String key, Object value) {
		if(key != null)
			this.session.setAttribute(key, value);
	}
	
	/**
	 * 设置Session请求参数
	 * @param paramertValue
	 */
	protected void setSessionAttribute(HashMap<String, Object> paramertValue) {
		if(paramertValue == null || paramertValue.isEmpty())
			return;
		for(String key : paramertValue.keySet()){
			this.setSessionAttribute(key, paramertValue.get(key));
		}
	}
	
	/**
	 * 设置Request请求参数
	 * @param paramertValue
	 */
	protected void setRequestAttribute(String key, Object value) {
		if(key != null)
			this.request.setAttribute(key, value);
	}
	
	/**
	 * 设置Request请求参数
	 * @param paramertValue
	 */
	protected void setRequestAttribute(HashMap<String, Object> paramertValue) {
		if(paramertValue == null || paramertValue.isEmpty())
			return;
		for(String key : paramertValue.keySet()){
			this.setRequestAttribute(key, paramertValue.get(key));
		}
	}

	/**
	 * Rorward 跳转到 Controller
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String forward(String url) {
		return REDIRECT_PREFIX + url;
	}

	/**
	 * Redirect 跳转到 Controller
	 * 
	 * example "redirect:/namespace/toController"
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String redirect(String url) {
		return "redirect:" + url;
	}

	/**
	 * Redirect 跳转到 Controller 带简单的参数
	 * 
	 * example "redirect:/namespace/toController?parmters="
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String redirect(String url, Map<String, String> parmters) {
		StringBuffer buffer = new StringBuffer("redirect:");
		buffer.append(url);
		if (parmters != null && parmters.size() > 0) {
			for (Map.Entry<String, String> entry : parmters.entrySet()) {
				buffer.append("?").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}
		return buffer.toString();
	}

	/**
	 * 返回一个 ModelAndView
	 * 
	 * @param viewName
	 * @return
	 */
	protected ModelAndView createModelAndView(String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	/**
	 * 直接输出。
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	protected void render(String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输 Object json对象.
	 */
	protected void renderJson(Object obj) {
		if (obj == null)
			render("", "application/json;charset=UTF-8");
		else
			render(JSONArray.toJSONString(obj), "application/json;charset=UTF-8");
	}

	/**
	 * 直接输json对象.
	 */
	protected void renderJson(String text) {
		render(text, "application/json;charset=UTF-8");
	}
	
	/**
	 * 直接输出纯字符串.
	 */
	protected void renderText(String text) {
		render(text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	protected void renderHtml(String html) {
		render(html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXML(String xml) {
		render(xml, "text/xml;charset=UTF-8");
	}

	/**
	 * 读取到服务器的路径息
	 * 
	 * @param relativePath
	 *            相对于服务器工程目录的路径
	 * @return
	 */
	protected String getServicePath(String relativePath) {
		return request.getSession().getServletContext().getRealPath(relativePath);
	}

	/**
	 * 在指定的相对路径下保存文件,返回相对于服务器的相对路径，包括文件名
	 * 
	 * @param request
	 * @param relativePath
	 * @param formFile
	 * @return
	 */
	protected String saveFileToService(HttpServletRequest request, String relativePath, File formFile) {
		return this.saveFileToService(request, relativePath, null, formFile);
	}

	/**
	 * 在指定的相对路径下保存文件,返回相对于服务器的相对路径，包括文件名
	 * 
	 * @param request
	 * @param relativePath
	 * @param fileName
	 * @param formFile
	 * @return
	 */
	protected String saveFileToService(HttpServletRequest request, String relativePath, String fileName,
			File formFile) {

		// 上传文件...

		return relativePath + "/" + fileName;

	}

	/**
	 * 在服务器上删除指定的文件，其中文件路径为相对于服务器所在目录的路径
	 * 
	 * @param request
	 * @param relativePath
	 * @return
	 */
	protected void deleteFileFromServcie(HttpServletRequest request, String relativePath) {

	}

	/**
	 * 设置客户端对网页不进行缓存
	 * 
	 * @param request
	 */
	protected void setClientNoCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

}
