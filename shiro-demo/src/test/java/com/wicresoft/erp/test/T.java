package com.wicresoft.erp.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class T {

	public static void main(String[] args) {

		Map<String, List<String>> permissionsRoleMap = new HashMap<String, List<String>>();		//存放URL，List<Role>
		permissionsRoleMap.put("123", new ArrayList<>());
		System.out.println(permissionsRoleMap);
		permissionsRoleMap.get("123").add("1");
		System.out.println(permissionsRoleMap);
		permissionsRoleMap.put("123", null);
		System.out.println(permissionsRoleMap);
		
		List<String> roles = new ArrayList<>();
		roles.add("1");
		roles.add("2");
		roles.add("3");
		String str = StringUtils.join(roles.toArray(), ",");
		System.out.println(str);
		
	}

}
