package icu.umatter.market_service.integration.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("test")
class HttpConfigurationTest {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity serverHttpSecurity
    ) {
        serverHttpSecurity.cors(ServerHttpSecurity.CorsSpec::disable);
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        serverHttpSecurity.authorizeExchange(
                authorizeExchangeSpec -> authorizeExchangeSpec
                        .anyExchange()
                        .permitAll()
        );
        return serverHttpSecurity.build();
    }
}
