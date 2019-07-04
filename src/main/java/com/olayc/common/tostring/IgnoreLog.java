package com.olayc.common.tostring;

import java.lang.annotation.*;

/**
 * 不打印日志
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IgnoreLog {

    /**
     * 不打印参数 默认打印
     *
     * @return
     */
    boolean argument() default false;

    /**
     * 不打印结果 默认不打印
     *
     * @return
     */
    boolean result() default true;
}
