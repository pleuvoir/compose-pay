package io.github.pleuvoir.manager.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.common.util.RequestUtil;
import io.github.pleuvoir.manager.model.po.pub.PubOperationLogPO;
import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;
import io.github.pleuvoir.manager.service.pub.PubOperationLogService;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;

/**
 * 配置操作日志切面<br>
 * 对controller中的方法配置了{@link RequiresPermissions}注解生效，另外，提供注解{@link NoOperationLog}取消日志记录
 */
@Aspect
public class OperationLogAop {
	
	private static Logger logger = LoggerFactory.getLogger(OperationLogAop.class);
	
	@Autowired
	private PubPermissionsService permissionsService;
	@Autowired
	private PubOperationLogService operationLogService;

	@Pointcut("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions)")
	private void controllerAspect() {}
	
	
	@Around("controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
		//全局变量判断是否需要记录操作日志
		if (!Const.RECORD_OPERATION_LOG) {
			return joinPoint.proceed();
		}
		//获取当前执行aop的方法
		Method method = getAopMethod(joinPoint);
		boolean hasRequiresPermissions = hasRequiresPermissionsAnnotation(method);
		boolean hasNoOperationLog = hasNoOperationLogAnnotation(method);
		//方法上存在一下情况时，不记录日志
		//1.存在NoOperationLog注解
		//2.不存在RequiresPermissions注解
		if(hasNoOperationLog || !hasRequiresPermissions) {
			logger.debug("不记录操作日志，NoOperationLog：{}，RequiresPermissions：{}", hasNoOperationLog, hasRequiresPermissions);
			return joinPoint.proceed();
		}
        
		//以下为记录操作日志的情况
		RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        //执行controller
        long beginTime = System.currentTimeMillis();
        
        Object result = null;
        try {
        	result = joinPoint.proceed();
        	long endTime = System.currentTimeMillis();
        	
        	//纪录日志
        	PubOperationLogPO succlog = doLogger(joinPoint, requiresPermissions.value(), PubOperationLogPO.STATUS_SUCCESS, endTime-beginTime, null);
        	if(logger.isDebugEnabled()) {
        		logger.debug("执行操作【成功】：{}，log：{}", StringUtils.join(requiresPermissions.value()), JSON.toJSONString(succlog));
        	}
        }catch(Throwable e) {
        	long endTime = System.currentTimeMillis();
        	//纪录日志
        	PubOperationLogPO errlog = doLogger(joinPoint, requiresPermissions.value(), PubOperationLogPO.STATUS_ERROR, endTime-beginTime, e);
       		logger.error("执行操作【失败】：{}，log：{}", StringUtils.join(requiresPermissions.value()), JSON.toJSONString(errlog));
        	throw e;
        }
		return result;
	}
	
	//判断方法上是否有RequiresPermissions注解
	private boolean hasRequiresPermissionsAnnotation(Method method) throws NoSuchMethodException, SecurityException {
        RequiresPermissions annRequiresPermissions = method.getAnnotation(RequiresPermissions.class);
        return annRequiresPermissions!=null;
	}
	
	//判断方法上是否有NoOperationLog注解
	private boolean hasNoOperationLogAnnotation(Method method) throws NoSuchMethodException, SecurityException {
        NoOperationLog annNoOperationLog = method.getAnnotation(NoOperationLog.class);
        return annNoOperationLog!=null;
	}
	
	//获取当前aop的方法
	private Method getAopMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MethodSignature)) {
        	throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature)signature;    
        Method targetMethod = methodSignature.getMethod();
        
        return joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), targetMethod.getParameterTypes());
	}
	
	
	//纪录日志
	private PubOperationLogPO doLogger(ProceedingJoinPoint joinPoint, String[] permissionCodes, String status, long time, Throwable e) {
		String username = (String)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		String controller = joinPoint.getTarget().getClass().getName();
		String method = joinPoint.getSignature().getName();
		
		PubOperationLogPO log = new PubOperationLogPO();
		log.setUsername(username);
		log.setController(controller);
		log.setMethod(method);
		log.setIp(getIp());
		
		if(ArrayUtils.isEmpty(permissionCodes)) {
			logger.warn("执行操作【异常】：权限编码为空，{}，log：{}", StringUtils.join(permissionCodes), JSON.toJSONString(log));
			return log;
		}
		//获取权限编码
		if(permissionCodes.length>1) {
			logger.warn("执行操作【异常】：权限注解上存在多个权限编码，将使用第一个权限编码，{}，log：{}", StringUtils.join(permissionCodes), JSON.toJSONString(log));
		}
		String permissionCode = permissionCodes[0];
		if(StringUtils.isBlank(permissionCode)) {
			logger.warn("执行操作【异常】：权限编码为空，{}，log：{}", permissionCode, JSON.toJSONString(log));
			return log;
		}
		
		PubPermissionPO permission = permissionsService.getPermission(permissionCode);
		if(permission==null) {
			logger.warn("执行操作【异常】：权限编码错误，未能查询到权限，{}，log：{}", permissionCode, JSON.toJSONString(log));
		}else {
			String menuId = permission.getMenuId();
			if(StringUtils.isBlank(menuId)) {
				logger.warn("执行操作【异常】：权限表中菜单ID为空，{}， permission：{}，log：{}", permissionCode, JSON.toJSONString(permission), JSON.toJSONString(log));
			}else {
				log.setMenuId(menuId);
				log.setPermissionId(permission.getId());
				log.setStatus(status);
				log.setElapsedTime(time);
				if(e!=null) {
					log.setRemark(ExceptionUtils.getStackTrace(e));
				}
				operationLogService.save(log);
			}
		}
		return log;
	}
	
	//获取IP
	private String getIp() {
		RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
		if(reqAttrs instanceof ServletRequestAttributes) {
			HttpServletRequest req = ((ServletRequestAttributes)reqAttrs).getRequest();
			return RequestUtil.ipAddress(req);
		}
		return null;
	}
	
}
