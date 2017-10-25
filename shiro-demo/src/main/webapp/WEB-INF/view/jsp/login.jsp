<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/common/taglib.jsp"%>

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
	<form action="loginCheck.shtml" method="post">
		<table style="margin-left: 20px;margin-top: 20px;">
			<tr>
				<td colspan="2"><b><c:if test="${not empty message}">${message}</c:if></b></td>
			</tr>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="username"/></td>
			</tr>
			<tr>
				<td>密 码：</td>
				<td><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td></td>
				<td style="padding-top: 8px;">
					<input type="submit" id="" value="登 陆"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>