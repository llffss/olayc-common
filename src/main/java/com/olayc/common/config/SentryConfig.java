package com.olayc.common.config;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 *
 *@author jianbiao11
 *@date 2018/10/30 16:28
 */
@Configuration
public class SentryConfig {

	@Bean
	public HandlerExceptionResolver sentryExceptionResolver() {
		return new io.sentry.spring.SentryExceptionResolver();
	}

	@Bean
	public ServletContextInitializer sentryServletContextInitializer() {
		return new io.sentry.spring.SentryServletContextInitializer();
	}
}
