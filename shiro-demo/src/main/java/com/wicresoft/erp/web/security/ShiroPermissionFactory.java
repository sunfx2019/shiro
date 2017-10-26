package com.wicresoft.erp.web.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;

import com.alibaba.fastjson.JSONArray;
import com.wicresoft.erp.service.IPermissService;
import com.wicresoft.erp.service.IUserService;

public class ShiroPermissionFactory extends ShiroFilterFactoryBean {

	private IUserService userService;
	
	private IPermissService permissService;

	public static String definition = "";	//配置文件中的过滤链
	
	//登录页面
	public final static String LOGIN_URL = "/login.shtml";
	//踢出登录提示
	public final static String KICKED_OUT_URL = "/error/kickedOut.jsp";
	//没有权限提醒
	public final static String UNAUTHORIZED_URL = "/error/403.jsp";
	
	/**
	 * 重写 初始化设置过滤链(添加db中的动态权限)
	 */
	@Override
	public void setFilterChainDefinitions(String definitions) {
		
		//System.out.println("\n********************** ShiroPermissionFactory.setFilterChainDefinitions **********************");
		//System.out.println("definitions:\n" + definitions);
		//System.out.println("**********************************************************************************************\n");
		
		definition = definitions;	// 记录配置的静态过滤链
		
		//返回 Map<RoleName, URL>
		List<Map<Object, Object>> list = permissService.findPermissionRole();		//查询DB中的角色权限URL关系
		//存放<URL,List<Role>>
		Map<String, List<String>> permissionsRoleMap = new HashMap<String, List<String>>();		
		Map<String, String> otherChains = new HashMap<String, String>();
		
		for(Map<Object, Object> map : list){
			//System.out.println(JSONArray.toJSON(map));
			String roleName = String.valueOf(map.get("name"));
			String url = String.valueOf(map.get("url"));
			if(permissionsRoleMap.containsKey(url)){
				List<String> roles = permissionsRoleMap.get(url);		//该URL所拥有的角色
				roles.add(roleName);									//追加Role
			}else{
				List<String> roles = new ArrayList<String>();
				roles.add(roleName);
				permissionsRoleMap.put(url, roles);
			}
		}

		//System.out.println(JSONArray.toJSON(permissionsRoleMap));
		Iterator<String> it = permissionsRoleMap.keySet().iterator();
		while(it.hasNext()){
			String url = it.next();
			List<String> roles = permissionsRoleMap.get(url);
			//System.out.println(url);
			//System.out.println(roles);
			//otherChains.put("/discover/newstag", "authc,roles[user,admin]");
			StringBuffer authcStr = new StringBuffer("authc,roles");
			authcStr.append("[");
			authcStr.append(StringUtils.join(roles.toArray(), ","));
			authcStr.append("]");
			otherChains.put(url, authcStr.toString());
		}
		//System.out.println("otherChains->" + JSONArray.toJSON(otherChains));
			
		// 加载配置默认的过滤链
		Ini ini = new Ini();
		ini.load(definitions);
		Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		}
		// 加上数据库中过滤链
		section.putAll(otherChains);
		setFilterChainDefinitionMap(section);
		System.out.println("section->" + JSONArray.toJSON(section));

	}
	
	/**
	 * 重新刷新权限配置
	 */
	public void reloadFilterChains() {
		
		synchronized (this) { // 强制同步，控制线程安全
			
			AbstractShiroFilter shiroFilter = null;

			try {
				shiroFilter = (AbstractShiroFilter) this.getObject();

				PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
				// 过滤管理器
				DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
				// 清除权限配置
				manager.getFilterChains().clear();
				this.getFilterChainDefinitionMap().clear();
				// 重新设置权限
				this.setFilterChainDefinitions(ShiroPermissionFactory.definition);	// 传入配置中的filterchains

				Map<String, String> chains = this.getFilterChainDefinitionMap();
				// 重新生成过滤链
				if (!CollectionUtils.isEmpty(chains)) {
					chains.forEach((url, definitionChains) -> {
						manager.createChain(url, definitionChains.trim().replace(" ", ""));
					});
				}
				// manager.addToChain("/discover/banner", "perms", "sssss");

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IPermissService getPermissService() {
		return permissService;
	}

	public void setPermissService(IPermissService permissService) {
		this.permissService = permissService;
	}


}
