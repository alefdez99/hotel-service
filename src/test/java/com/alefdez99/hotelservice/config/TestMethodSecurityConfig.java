package com.alefdez99.hotelservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;

// Configuración de seguridad para pruebas unitarias y de integración
@TestConfiguration
public class TestMethodSecurityConfig {

    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler() {
        return new DefaultMethodSecurityExpressionHandler();
    }
}
