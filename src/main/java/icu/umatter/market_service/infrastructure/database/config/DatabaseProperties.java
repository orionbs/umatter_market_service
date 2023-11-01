package icu.umatter.market_service.infrastructure.database.config;

import icu.umatter.market_service.infrastructure.database.property.SourceDatabaseProperty;
import icu.umatter.market_service.infrastructure.database.property.VersionDatabaseProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseProperties {

    @Bean(value = "sourceDatabaseProperty")
    @ConfigurationProperties(prefix = "database.source")
    public SourceDatabaseProperty sourceDatabaseProperty() {
        return new SourceDatabaseProperty();
    }

    @Bean(value = "versionDatabaseProperty")
    @ConfigurationProperties(prefix = "database.version")
    public VersionDatabaseProperty versionDatabaseProperty() {
        return new VersionDatabaseProperty();
    }

}
