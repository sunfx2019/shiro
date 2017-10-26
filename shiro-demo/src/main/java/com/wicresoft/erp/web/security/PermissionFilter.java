package com.wicresoft.erp.web.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import com.wicresoft.erp.core.util.HttpRequestUtil;

/**
 * 资源URL校验 Filter
 * 
 * @author Administrator
 *
 */
public class PermissionFilter extends AccessControlFilter {
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		System.out.println("***************************************************************");
		System.out.println("PermissionFilter.isAccessAllowed");
		System.out.println("***************************************************************");
		
		// 先判断带参数的权限判断
		Subject subject = getSubject(request, response);
		if (null != mappedValue) {
			String[] arra = (String[]) mappedValue;
			for (String permission : arra) {
				if (subject.isPermitted(permission)) {
					return Boolean.TRUE;
				}
			}
		}

		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);

		//去除根目录
		String uri = httpRequest.getRequestURI();	// 获取URI
		String basePath = httpRequest.getContextPath();	// 获取basePath

		if (null != uri && uri.startsWith(basePath)) {
			uri = uri.replace(basePath, "");
		}

		if (subject.isPermitted(uri)) {
			return Boolean.TRUE;
		}

		if (HttpRequestUtil.isAjax(request)) {
			Map<String, String> resultMap = new HashMap<String, String>();
			//System.out.println("当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "当前用户没有登录！");
			HttpRequestUtil.renderJson(httpResponse, resultMap);
		}

		return Boolean.FALSE;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (null == subject.getPrincipal()) { // 表示没有登录，重定向到登录页面
			saveRequest(request);
			WebUtils.issueRedirect(request, response, ShiroPermissionFactory.LOGIN_URL);
		} else {
			if (StringUtils.hasText(ShiroPermissionFactory.UNAUTHORIZED_URL)) { // 如果有未授权页面跳转过去
				WebUtils.issueRedirect(request, response, ShiroPermissionFactory.UNAUTHORIZED_URL);
			} else {// 否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return Boolean.FALSE;
	}

}
