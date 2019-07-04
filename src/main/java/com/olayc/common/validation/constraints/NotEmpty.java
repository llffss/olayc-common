package com.olayc.common.validation.constraints;

import com.olayc.common.validation.validator.NotEmptyValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {NotEmptyValidator.class})
public @interface NotEmpty {

	//默认错误消息
	public String message() default "非法参数";

	//分组
	Class<?>[] groups() default {};

	//负载
	Class<?>[] payload() default {};

	//指定多个时使用
	@Target({FIELD, PARAMETER})
	@Retention(RUNTIME)
	@Constraint(validatedBy = {NotEmptyValidator.class})
	@interface List {
		NotEmpty[] value();
	}

}