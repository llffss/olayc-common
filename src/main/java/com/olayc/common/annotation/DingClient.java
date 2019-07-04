package com.olayc.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DingClient {

    /*附带信息*/
    String desc() ;

    String msgtype() default "text";

}
