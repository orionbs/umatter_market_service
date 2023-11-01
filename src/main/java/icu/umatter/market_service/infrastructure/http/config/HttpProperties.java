package icu.umatter.market_service.infrastructure.http.config;

import icu.umatter.market_service.infrastructure.http.property.SecurityHttpProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpProperties {

    @Bean(value = "securityHttpProperty")
    @ConfigurationProperties(prefix = "http.security")
    public SecurityHttpProperty securityHttpProperty() {
        return new SecurityHttpProperty();
    }

}
