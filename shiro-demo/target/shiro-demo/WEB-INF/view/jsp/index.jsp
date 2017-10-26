<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/jsp/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>index.jsp</title>
	<style type="text/css">
		body,div,table,sapn{
			font-size: 12px;
			font-family: '微软雅黑';
		}
	</style>
</head>
<body>
	index.jsp
	<hr>
	<a href="<c:url value="/loginOut.shtml"/>">退出</a>
	<shiro:hasRole name="admin">
		哈罗~
	</shiro:hasRole>	
</body>
</html>