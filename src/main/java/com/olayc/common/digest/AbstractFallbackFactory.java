package com.olayc.common.digest;

import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;

/**
 * @author hanya
 */
public abstract  class  AbstractFallbackFactory<T> implements FallbackFactory<T> {
    private static ThreadLocal<Throwable> errorThreadLocal = new ThreadLocal();
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取fallback对象
     * @param cause
     * @return
     */
    protected T getFallBallback(Throwable cause){
        errorThreadLocal.set(cause);
        Class<T> tClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return applicationContext.getBean(tClass);
    }

    /**
     * 获取fallback的异常信息
     * @return
     */
    public static Throwable getFallbackError(){
        return errorThreadLocal.get();
    }

    /**
     * 移除fallback的异常信息
     */
    public static void removeFallbackError(){
        errorThreadLocal.remove();
    }
}
