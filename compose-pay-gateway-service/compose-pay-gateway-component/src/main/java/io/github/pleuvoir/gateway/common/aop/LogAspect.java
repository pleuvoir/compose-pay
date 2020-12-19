package io.github.pleuvoir.gateway.common.aop;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.gateway.constants.ResultCodeEnum;
import io.github.pleuvoir.gateway.model.vo.Result;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 *
 * @author <a href="mailto:fuwei@daojia-inc.com">pleuvoir</a>
 */
@Slf4j
@Order(1)
@Aspect
@Component
public class LogAspect {


    @Around("execution(* io.github.pleuvoir.gateway.component..*.*(..)) && @annotation(io.github.pleuvoir.gateway.common.aop.Log)")
    public Object around(ProceedingJoinPoint point) {

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Object[] args = point.getArgs();
        String methodName = methodSignature.getMethod().getName();

        String businessName = methodSignature.getMethod().getAnnotation(Log.class).value();
        Object first = args[0];

        log.info("{} - {} in param={}", methodName, businessName, JSON.toJSONString(first));

        Object retObj;
        try {
            StopWatch watch = StopWatch.createStarted();
            retObj = point.proceed(args);
            watch.stop();
            log.info("{} - {} in param={}，out param={}，cost={}ms", methodName, businessName, JSON.toJSONString(first),
                    JSON.toJSONString(retObj), watch.getTime(TimeUnit.MILLISECONDS));
        } catch (Throwable e) {
            log.error("系统异常", e);
            return Result.fail(ResultCodeEnum.ERROR);
        }

        return retObj;
    }

}