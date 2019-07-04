package com.olayc.common.digest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 切面基本类
 *
 * @author liuyan
 * @date 2018-09-10
 */
class BaseDigestAspect {

    /**
     * 请求正常
     */
    private static final String TRUE_VIEW = "T";

    /**
     * 请求异常
     */
    private static final String FALSE_VIEW = "F";

    protected String getResultView(boolean processSuccess) {
        return processSuccess ? TRUE_VIEW : FALSE_VIEW;
    }

    protected long processTakeMills(long requestTimeMills) {
        return System.currentTimeMillis() - requestTimeMills;
    }

    protected Method getInvokeMethod(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            return ((MethodSignature) signature).getMethod();
        }
        return null;
    }

    protected Class<?> getInvokeClass(ProceedingJoinPoint joinPoint) {
        Method method = getInvokeMethod(joinPoint);
        if (method != null) {
            return method.getDeclaringClass();
        }
        return null;
    }
}
