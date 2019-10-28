package io.github.pleuvoir.gateway.common.aop;

import io.github.pleuvoir.gateway.common.utils.HibernateValidatorUtils;
import io.github.pleuvoir.gateway.common.utils.ValidationResult;
import io.github.pleuvoir.gateway.constants.RspCodeEnum;
import io.github.pleuvoir.gateway.model.vo.ResultMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Around("execution(* io.github.pleuvoir.gateway..*.*(..))")
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
                    return ResultMessageVO.fail(RspCodeEnum.PARAM_ERROR, validationResult.getErrorMessageOneway());
                }
            }
        }
        return point.proceed();
    }
}
