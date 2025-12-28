package com.equalcollective.xray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot Application for X-Ray Debugger
 * 
 * Provides REST API for debugging multi-step decision pipelines.
 */
@SpringBootApplication
public class XRayDebuggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRayDebuggerApplication.class, args);
        System.out.println("\n" +
                "===========================================\n" +
                "  X-Ray Debugger API Started Successfully  \n" +
                "  API: http://localhost:8080/api           \n" +
                "  H2 Console: http://localhost:8080/h2-console\n" +
                "===========================================\n");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
