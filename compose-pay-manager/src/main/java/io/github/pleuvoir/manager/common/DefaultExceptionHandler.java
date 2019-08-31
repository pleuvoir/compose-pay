package io.github.pleuvoir.manager.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

/**
 * 提供通用的异常处理，包括打印异常、输出统一的错误信息
 * @author abeir
 *
 */
public class DefaultExceptionHandler extends SimpleMappingExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	
	private static final String SPACE = StringUtils.repeat(StringUtils.SPACE, 4);
	
	//错误的json信息
	private String defaultErrorJson;
	
	private Properties exceptionMappingsJson;
	
	/**
	 * 设置json返回时的错误json信息
	 * @param defaultErrorJson
	 */
	public void setDefaultErrorJson(String defaultErrorJson) {
		this.defaultErrorJson = defaultErrorJson;
	}
	
	/**
	 * 设置发生异常时，返回的json信息
	 * @param mappings
	 */
	public void setExceptionMappingsJson(Properties mappings) {
		this.exceptionMappingsJson = mappings;
	}
	
	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		logger.error(buildLogMessage(ex, request));
	}
	
	@Override
	protected String buildLogMessage(Exception ex, HttpServletRequest request) {
		StringBuilder b = new StringBuilder();
		b.append(super.buildLogMessage(ex, request));
		
		b.append(StringUtils.CR);
		b.append(StringUtils.LF);
		
		StackTraceElement[] sts = ex.getStackTrace();
		if(ArrayUtils.isNotEmpty(sts)){
			for(StackTraceElement st : sts){
				b.append(SPACE);
				b.append(st.toString());
				b.append(StringUtils.CR);
				b.append(StringUtils.LF);
			}
		}
		return b.toString();
	}
	
	/**
	 * 根据controller方法上是否存在ResponseBody注解来决定返回的错误信息使用web页面还是json
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		boolean isReturnJson = isReturnJson(handler);
		
		if(isReturnJson) {
			FastJsonJsonView view  = new FastJsonJsonView();
			
			Map<String,Object> errorJson = null;
			//设置了指定异常错误json
			if(exceptionMappingsJson!=null) {
				String errJsonString = findMatchingJson(exceptionMappingsJson, ex);
				if(StringUtils.isNotBlank(errJsonString)) {
					errorJson = JSON.parseObject(errJsonString, HashMap.class);
				}
			}
			//未设置指定异常错误json，指定了公共的异常错误json
			if(MapUtils.isEmpty(errorJson) && StringUtils.isNotBlank(defaultErrorJson)) {
				errorJson = JSON.parseObject(defaultErrorJson, HashMap.class);
			}
			//公共的异常错误json有误时，最后一层补救
			if(MapUtils.isEmpty(errorJson)) {
				Map<String,String> attrs = new HashMap<>();
				attrs.put("code", "500");
				attrs.put("msg", ex.getMessage());
				view.setAttributesMap(attrs);
			}else {
				view.setAttributesMap(errorJson);
			}
			return new ModelAndView(view);
		}
		return super.doResolveException(request, response, handler, ex);
	}
	
	
	private boolean isReturnJson(Object handler) {
		if(handler instanceof HandlerMethod) {
			//检查是否存在ResponseBody注解
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
			if(responseBody==null) {
				responseBody = handlerMethod.getBean().getClass().getAnnotation(ResponseBody.class);
			}
			if(responseBody!=null) {
				return true;
			}
		}
		return false;
	}
	
	private String findMatchingJson(Properties exceptionMappingsJson, Exception ex) {
		return super.findMatchingViewName(exceptionMappingsJson, ex);
	}
}
