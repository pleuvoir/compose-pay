package io.github.pleuvoir.channel.common.aop;

import io.github.pleuvoir.channel.common.util.HibernateValidatorUtils;
import io.github.pleuvoir.channel.common.util.ValidationResult;
import io.github.pleuvoir.channel.exception.ChannelServiceException;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 验证切面
 *
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
 */
@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Around("execution(* io.github.pleuvoir.channel..*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object[] args = point.getArgs();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Valid.class)) {
                Object val = args[i];
                ValidationResult validationResult = HibernateValidatorUtils.validateEntity(val);
                if (validationResult.isHasErrors()) {
                    throw new ChannelServiceException(ResultCodeEnum.PARAM_ERROR, validationResult.getErrorMessageOneway());
                }
            }
        }
        return point.proceed();
    }
}
