package com.olayc.common.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Deprecated
public @interface ParamCheck {

	//分组
	Class<?>[] groups() default {};

}
