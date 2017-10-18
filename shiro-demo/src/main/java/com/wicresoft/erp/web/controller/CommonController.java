package com.wicresoft.erp.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.wicresoft.erp.bean.RequestToMethodItem;
import com.wicresoft.erp.web.controller.AbctractBaseController;

/**
 * 公共Controller
 * 
 * @author sunfx
 *
 */
@Controller
public class CommonController extends AbctractBaseController {

	private static final String mappings_jsp = "mappingList";
	
	/**
	 * 查看全部的 Controller 映射的URL
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mappingList.shtml")
	public String mappings() {
		
		// 获取上下文对象
		WebApplicationContext appContext = (WebApplicationContext) request.getAttribute(DispatcherServlet.WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		// 请求url和处理方法的映射
		List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
		// 获取所有的RequestMapping
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext, HandlerMapping.class, true, false);
		for (HandlerMapping handlerMapping : allRequestMappings.values()) {
			// RequestMapping对象
			if (handlerMapping instanceof RequestMappingHandlerMapping) {
				RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
				Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
				for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
					RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
					HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
					RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
					PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
					String requestType = null;
					String requestUrl = null;
					//String requestType = methodCondition.getMethods().toString();
					//String requestUrl = patternsCondition.getPatterns().toString();
					Iterator<String> urlIterator =  patternsCondition.getPatterns().iterator();
					Iterator<RequestMethod> methodIterator =  methodCondition.getMethods().iterator();
					if(methodIterator.hasNext()){
						requestType = methodIterator.next().name();
					}
					if(urlIterator.hasNext()){
						requestUrl = urlIterator.next();
					}
					String controllerName = mappingInfoValue.getBeanType().getName();
					String requestMethodName = mappingInfoValue.getMethod().getName();
					Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
					Class<?> methodReturnTypes = mappingInfoValue.getMethod().getReturnType();
					RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName, methodParamTypes, methodReturnTypes);
					requestToMethodItemList.add(item);
				}
			}
		}
		
		this.setRequestAttribute("requestToMethodItemList", requestToMethodItemList);
		
		return mappings_jsp;
		
	}
	
}
