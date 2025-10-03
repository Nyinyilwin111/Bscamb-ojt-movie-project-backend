package com.UserList.API_demo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configure implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
	  System.out.println("hello world");
    registry.addMapping("/**").allowedMethods(CorsConfiguration.ALL).allowedOrigins("http://localhost:8080");
    
  }

}