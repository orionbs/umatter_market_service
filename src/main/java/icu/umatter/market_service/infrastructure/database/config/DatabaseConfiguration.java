package icu.umatter.market_service.infrastructure.database.config;

import icu.umatter.market_service.infrastructure.database.property.SourceDatabaseProperty;
import icu.umatter.market_service.infrastructure.database.property.VersionDatabaseProperty;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableR2dbcRepositories(basePackages = "icu.umatter.market_service.infrastructure.database")
@EnableR2dbcAuditing(auditorAwareRef = "auditorAware")
public class DatabaseConfiguration {

    @Bean(value = "connectionFactory")
    public ConnectionFactory connectionFactory(
            @Qualifier(value = "sourceDatabaseProperty") SourceDatabaseProperty sourceDatabaseProperty
    ) {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(
                                ConnectionFactoryOptions.DRIVER,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getDriver)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.HOST,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getHost)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.PORT,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getPort)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.USER,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getUser)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.PASSWORD,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getPassword)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.DATABASE,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getDatabase)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .build()
        );
    }

    @Bean(value = "entityTemplate")
    public R2dbcEntityOperations r2dbcEntityOperations(
            @Qualifier(value = "connectionFactory") ConnectionFactory connectionFactory
    ) {
        DefaultReactiveDataAccessStrategy defaultReactiveDataAccessStrategy = new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .bindMarkers(PostgresDialect.INSTANCE.getBindMarkersFactory())
                .build();
        return new R2dbcEntityTemplate(databaseClient, defaultReactiveDataAccessStrategy);
    }

    @Bean(value = "dataSource")
    public DataSource dataSource(
            @Qualifier(value = "sourceDatabaseProperty") SourceDatabaseProperty sourceDatabaseProperty
    ) {
        return new SingleConnectionDataSource(
                "jdbc:postgresql://" + Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getHost).orElseThrow() + ":" + Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getPort).orElseThrow() + "/" + Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getDatabase).orElseThrow(),
                Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getUser).orElseThrow(),
                Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getPassword).orElseThrow(),
                true
        );
    }

    @Bean(value = "liquibase")
    public SpringLiquibase springLiquibase(
            @Qualifier(value = "dataSource") DataSource dataSource,
            @Qualifier(value = "versionDatabaseProperty") VersionDatabaseProperty versionDatabaseProperty
    ) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setShouldRun(
                Optional.of(versionDatabaseProperty)
                        .map(VersionDatabaseProperty::isEnabled)
                        .orElse(Boolean.FALSE)
        );
        springLiquibase.setChangeLog(
                Optional.of(versionDatabaseProperty)
                        .map(VersionDatabaseProperty::getChangeLog)
                        .orElseThrow()
        );
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

    @Bean(value = "auditorAware")
    public ReactiveAuditorAware<UUID> auditorAware() {
        return () -> ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(authentication -> UUID.randomUUID());
    }

}
