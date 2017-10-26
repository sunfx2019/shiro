package com.wicresoft.erp.web.security;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
 import org.springframework.core.io.ClassPathResource;

import com.wicresoft.erp.core.util.ApplicationContextFactoryUtil;
import com.wicresoft.erp.core.util.INI4j;

public class ShiroFilterFactoryManager {
	
	private final String SHIROFILTERFACTORYBEAN = "shiroFilterFactoryBean";
	
	// 注意/r/n前不能有空格
	private static final String CRLF = "\r\n";

	public String loadFilterChainDefinitions() {
		StringBuffer sb = new StringBuffer();
		sb.append(getINIAuthRule());
		System.out.println("\n***************** shiro_auth.ini *****************");
		System.out.println(sb.toString());
		System.out.println("**************************************************\n");
		return sb.toString(); 
	}
	
	/**
	 * 从配额文件获取固定权限验证规则串
	 */
	private String getINIAuthRule(){
		String fileName = "shiro_auth.ini";
		ClassPathResource cp = new ClassPathResource(fileName);
		INI4j ini = null;
		try {
			ini = new INI4j(cp.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String section = "base_auth";
		Set<String> keys = ini.get(section).keySet();
		StringBuffer sb = new StringBuffer();
		for (String key : keys) {
			String value = ini.get(section, key);
			sb.append(key).append(" = ")
			.append(value).append(CRLF);
		}
		return sb.toString();
	}

	// 此方法加同步锁
	public synchronized void reCreateFilterChains() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = (ShiroFilterFactoryBean) ApplicationContextFactoryUtil.getBean(SHIROFILTERFACTORYBEAN);
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

		// 清空老的权限控制
		manager.getFilterChains().clear();

		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());	//加载INI配置
		
		// 重新构建生成
		Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}

	}

}

