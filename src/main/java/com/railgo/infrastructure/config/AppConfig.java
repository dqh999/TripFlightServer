package com.railgo.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {
////    @Bean
////    public FilterRegistrationBean<CorsFilter> corsFilter() {
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        CorsConfiguration config = new CorsConfiguration();
////        config.setAllowCredentials(true);
////
////        config.addAllowedOrigin("http://localhost:8080");
////        config.addAllowedOrigin("http://api.railgo.dqhdev.io.vn");
////
////
////        config.addAllowedHeader("*");
////        config.addAllowedMethod("*");
////        config.addExposedHeader("Access-Control-Allow-Origin");
////        source.registerCorsConfiguration("/**", config);
////        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
////        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
////        return bean;
////    }
}