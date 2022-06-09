package com.selex.motor.config;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {

	@Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

	@Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    	registry.addResourceHandler(
	                "/images/**",
	                "/css/**",
	                "/js/**")
	                .addResourceLocations(
	                        "classpath:/statics/images/",
	                        "classpath:/statics/css/",
	                        "classpath:/statics/js/");
	    }
}
