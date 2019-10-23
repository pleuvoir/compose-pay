package io.github.pleuvoir.gateway.common.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import io.github.pleuvoir.gateway.common.utils.HibernateValidatorUtils;
import io.github.pleuvoir.gateway.common.utils.ValidationResult;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;

import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.model.vo.ResultSignMessageVO;
import lombok.extern.slf4j.Slf4j;

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
        try {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(Valid.class)) {
                    Object val = args[i];
                    ValidationResult validationResult = HibernateValidatorUtils.validateEntity(val);
                    if (validationResult.isHasErrors()) {
                        String errorMsg = validationResult.getErrorMsg().toString();
                        log.warn("数据验证错误：{}", errorMsg);
                        return new ResultSignMessageVO(ResultCodeEnum.PARAM_ERROR, "mid", errorMsg);
                    }
                }
            }
            return point.proceed();
        } catch (Exception e) {
            log.error("验证切面系统异常", e);
            return new ResultSignMessageVO(ResultCodeEnum.ERROR, "mid", ResultCodeEnum.ERROR.getMsg());
        }
    }
}
