package icu.umatter.market_service.infrastructure.http.config;

import icu.umatter.market_service.infrastructure.http.property.SecurityHttpProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Optional;

@Configuration
@Profile("!test")
public class HttpConfiguration {

    @Bean(value = "reactiveJwtDecoder")
    public ReactiveJwtDecoder reactiveJwtDecoder(
            @Qualifier(value = "securityHttpProperty") SecurityHttpProperty securityHttpProperty
    ) {
        return NimbusReactiveJwtDecoder
                .withIssuerLocation(
                        Optional.of(securityHttpProperty)
                                .map(SecurityHttpProperty::getIssuerUrl)
                                .map(String::trim)
                                .orElseThrow()
                )
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity serverHttpSecurity,
            @Qualifier(value = "reactiveJwtDecoder") ReactiveJwtDecoder reactiveJwtDecoder
    ) {
        serverHttpSecurity.cors(ServerHttpSecurity.CorsSpec::disable);
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);
        serverHttpSecurity.authorizeExchange(
                authorizeExchangeSpec -> authorizeExchangeSpec
                        .anyExchange()
                        .authenticated()
        );
        serverHttpSecurity.oauth2ResourceServer(
                oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(
                                jwtSpec -> jwtSpec
                                        .jwtDecoder(reactiveJwtDecoder)
                        )
        );
        return serverHttpSecurity.build();
    }

}
