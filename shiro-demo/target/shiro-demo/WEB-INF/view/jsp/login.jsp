<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/taglib.jsp"%>

<html>
<head>
	<style type="text/css">
		body,table,div,span{
			font-size: 12px;
			font-family: '微软雅黑';
		}
	</style>
</head>
<body>
	<c:if test="${not empty message}">${message}</c:if>
	<br><br>
	<form action="loginCheck.shtml" method="post">
		用户名：<input type="text" name="username"/>
		<br>
		<br>
		用户名：<input type="text" name="password"/>
		<br>
		<br>
		<input type="submit" id="" value="登 陆"/>
	</form>
</body>
</html>