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
					// 需要校验
					Object val = args[i];
					if (Objects.isNull(args[i])) {
						return new ResultSignMessageVO(ResultCodeEnum.PARAM_ERROR, "mid", "参数不能为null");
					}
					ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
					javax.validation.Validator validator = validatorFactory.getValidator();
					Set<ConstraintViolation<Object>> set = validator.validate(val);
					if (CollectionUtils.isNotEmpty(set)) {
						String errorMessage = Joiner.on("; ").join(set.stream().map(ConstraintViolation::getMessage)
								.sorted(String::compareTo).collect(Collectors.toList()));
						return new ResultSignMessageVO(ResultCodeEnum.PARAM_ERROR, "mid", errorMessage);
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
