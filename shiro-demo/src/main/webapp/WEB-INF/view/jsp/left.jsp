<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/jsp/common/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>导航栏</div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="${pageContext.request.contextPath}/resources/images/leftico01.png" /></span>用户管理
    </div>
    	<ul class="menuson">
	        <li class=""><cite></cite><a href="${pageContext.request.contextPath}/user/list.shtml" target="rightFrame">用户列表</a><i></i></li>
	        <li><cite></cite><a href="${pageContext.request.contextPath}/role/list.shtml" target="rightFrame">角色管理</a><i></i></li>
	        <li><cite></cite><a href="${pageContext.request.contextPath}/resources/list.shtml" target="rightFrame">资源管理</a><i></i></li>
        </ul>    
    </dd>
        
    
    <dd>
    <div class="title">
    <span><img src="${pageContext.request.contextPath}/resources/images/leftico02.png" /></span>学生管理
    </div>
    	<ul class="menuson">
	        <li><cite></cite><a href="#">xxxx</a><i></i></li>
	        <li><cite></cite><a href="#">xxxx</a><i></i></li>
	        <li><cite></cite><a href="#">xxxx</a><i></i></li>
        </ul>     
    </dd> 
    
    
    <dd><div class="title"><span><img src="${pageContext.request.contextPath}/resources/images/leftico03.png" /></span>教师管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="#">xxxx</a><i></i></li>
        <li><cite></cite><a href="#">xxxx</a><i></i></li>
        <li><cite></cite><a href="#">xxxx</a><i></i></li>
        <li><cite></cite><a href="#">xxxx</a><i></i></li>
    </ul>    
    </dd>  
    
    
    <dd><div class="title"><span><img src="${pageContext.request.contextPath}/resources/images/leftico04.png" /></span>班级管理</div>
    <ul class="menuson">
        <li><cite></cite><a href="#">自定义</a><i></i></li>
        <li><cite></cite><a href="#">常用资料</a><i></i></li>
        <li><cite></cite><a href="#">信息列表</a><i></i></li>
        <li><cite></cite><a href="#">其他</a><i></i></li>
    </ul>
    </dd>  
    
    </dl>
    
</body>
</html>