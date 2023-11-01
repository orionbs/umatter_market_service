package icu.umatter.market_service.integration.database.config;

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
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableR2dbcRepositories(basePackages = "icu.umatter.market_service.infrastructure.database")
@EnableR2dbcAuditing
@ActiveProfiles("test")
public class DatabaseConfigurationTest {

    @Bean(value = "connectionFactory")
    public ConnectionFactory connectionFactory(
            @Qualifier(value = "sourceDatabaseProperty") SourceDatabaseProperty sourceDatabaseProperty
    ) {
        return ConnectionFactories.get(
                ConnectionFactoryOptions
                        .builder()
                        .option(
                                ConnectionFactoryOptions.DRIVER,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getDriver)
                                        .map(String::trim)
                                        .orElseThrow()
                        )
                        .option(
                                ConnectionFactoryOptions.PROTOCOL,
                                Optional.of(sourceDatabaseProperty)
                                        .map(SourceDatabaseProperty::getProtocol)
                                        .map(String::trim)
                                        .filter("mem"::equals)
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
    public R2dbcEntityOperations entityTemplate(
            @Qualifier(value = "connectionFactory") ConnectionFactory connectionFactory
    ) {
        DefaultReactiveDataAccessStrategy defaultReactiveDataAccessStrategy = new DefaultReactiveDataAccessStrategy(H2Dialect.INSTANCE);
        DatabaseClient databaseClient = DatabaseClient.builder()
                .connectionFactory(connectionFactory)
                .bindMarkers(H2Dialect.INSTANCE.getBindMarkersFactory())
                .build();
        return new R2dbcEntityTemplate(databaseClient, defaultReactiveDataAccessStrategy);
    }

    @Bean(value = "dataSource")
    public DataSource dataSource(
            @Qualifier(value = "sourceDatabaseProperty") SourceDatabaseProperty sourceDatabaseProperty
    ) {
        return new SingleConnectionDataSource(
                "jdbc:h2:mem:" + Optional.of(sourceDatabaseProperty).map(SourceDatabaseProperty::getDatabase).orElseThrow(),
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
        springLiquibase.setDropFirst(Boolean.FALSE);
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
        return () -> Mono.just(UUID.randomUUID());
    }

}
