package com.olayc.common.validation.constraints;

import com.olayc.common.validation.validator.PhoneNoValidator;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 示例
 * 日后非常常用的验证放到此处
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {PhoneNoValidator.class})
public @interface PhoneNo {

	//默认错误消息
	public String message() default "手机号格式不正确";

	//分组
	Class<?>[] groups() default {};

	//负载
	Class<?>[] payload() default {};

	//指定多个时使用
	@Target({FIELD, PARAMETER})
	@Retention(RUNTIME)
	@Constraint(validatedBy = {PhoneNoValidator.class})
	@interface List {
		PhoneNo[] value();
	}

}


