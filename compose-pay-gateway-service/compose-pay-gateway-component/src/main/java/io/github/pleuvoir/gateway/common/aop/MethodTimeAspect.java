package io.github.pleuvoir.gateway.common.aop;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;


@Slf4j
@Component
@Aspect
@SuppressWarnings("rawtypes")
public class MethodTimeAspect {

    //毫秒  
    private static final long MILLISECOND = 0;

    private static final String MID_FIELD = "mid";
    private static final String SERIAL_NO_FIELD = "serialNo";
    private static final String ORDER_NO_FIELD = "orderNo";

    @Pointcut("@annotation(io.github.pleuvoir.gateway.common.aop.MethodTimeLog)")
    public void methodPointcut() {

    }

    /**
     * 统计方法执行耗时Around环绕通知
     */
    @Around("methodPointcut()")
    public Object timeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 定义返回对象、得到方法需要的参数  
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(className);
        Method[] methods = targetClass.getMethods();
        String businessName = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    businessName = method.getAnnotation(MethodTimeLog.class).value();
                    break;
                }
            }
        }

        Object retObj = null;
        try {
            log.info("-=- {} 入参={}",businessName, Arrays.asList(arguments));
            retObj = joinPoint.proceed(arguments);
        } catch (Throwable e) {
            String remark = "异常信息：" + e.getMessage();
            throw e;
        }
        log.info("-=- {} 出参={}",businessName, JSON.toJSONString(retObj));
        return retObj;
    }


    private String getFieldValueByNameFromArguments(Object[] arguments, String fieldName) {
        if (arguments == null || arguments.length == 0) {
            return "";
        }
        String value = "";
        for (Object obj : arguments) {
            value = getFieldValueByName(obj, fieldName);
            if (StringUtils.isNotEmpty(value)) {
                return value;
            }
        }
        return value;
    }

    /**
     * 根据属性名获取属性值
     */
    public String getFieldValueByName(Object o, String fieldName) {
        if (o == null) {
            return "";
        }
        //判断是不是商户号
        if (o instanceof String && MID_FIELD.equals(fieldName)) {
            String content = o.toString();
            String pattern = "[0-9]{15}";
            boolean isMid = Pattern.matches(pattern, content);
            if (isMid) {
                return content;
            }
        }
        //判断是不是平台订单号
        if (o instanceof String && (SERIAL_NO_FIELD.equals(fieldName) || ORDER_NO_FIELD.equals(fieldName))) {
            String content = o.toString();
            String pattern = "[Z|D]F[0-9]{20}";
            boolean isSerialNo = Pattern.matches(pattern, content);
            if (isSerialNo) {
                return content;
            }
        }

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value.toString();
        } catch (Exception ignore) {
        }
        return StringUtils.EMPTY;
    }

}  
