package com.olayc.common.annotation;

import java.lang.annotation.*;

/**
 * 根据返回结果报警
 * @author GW00167781
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Alarm {
    String [] value();
}
