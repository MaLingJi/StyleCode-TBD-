package com.outfit_share;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.outfit_share.util.JwtInterceptor;

@Configuration
public class JwtConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration reg = registry.addInterceptor(jwtInterceptor);

        // 加入需要攔截的Patterns
        reg.addPathPatterns("**/**/admin/**")
                .addPathPatterns("/member/**")
        		.addPathPatterns("/pay/**")
        		.addPathPatterns("/order/admin/**")
        		.addPathPatterns("/order/**");
	
    }
    
}
