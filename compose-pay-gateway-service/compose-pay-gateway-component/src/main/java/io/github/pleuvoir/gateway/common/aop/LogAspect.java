/*
 * Copyright © 2020 pleuvoir (pleuvior@foxmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.pleuvoir.gateway.common.aop;

import com.alibaba.fastjson.JSON;
import io.github.pleuvoir.pay.common.enums.ResultCodeEnum;
import io.github.pleuvoir.pay.common.model.Result;
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
 * @author <a href="mailto:pleuvior@foxmail.com">pleuvoir</a>
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