<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/taglib.jsp"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>请求方法列表</title>
	<style type="text/css">
		body{ margin: 20px; }
		td, th { padding: 5px; font-size: 12px; }
		td{ word-break:break-all;  word-wrap:break-word; }
	</style>
</head>
<body>

	${requestToMethodItemList}
	
	<c:out value="${requestToMethodItemList}"></c:out>
	
	<table border="1" width="">
		<caption><h1>请求方法列表</h1></caption>
		<tr height="50">
			<th>请求URL</th>
			<th>控制器类</th>
			<th>类方法</th>
			<th>请求方式</th>
			<th>入参</th>
			<th>出参</th>
		</tr>
		<c:if test="${not empty requestToMethodItemList}">
			<c:forEach var="v" items="${requestToMethodItemList}">
				<tr>
					<td>${v.requestUrl}</td>
					<td>${v.controllerName}</td>
					<td>${v.requestMethodName}</td>
					<td>${v.requestType}</td>
					<td>
						<c:if test="${not empty v.methodParamTypes}">
							<c:forEach var="p" items="${v.methodParamTypes}">
								<div>${p}</div>
							</c:forEach>
						</c:if>
					</td>
					<td>${v.methodReturnTypes}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</body>
</html>