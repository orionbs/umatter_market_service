package icu.umatter.market_service.infrastructure.database.property;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SourceDatabaseProperty {
    @NotNull(message = "Database driver can't be null.")
    private String driver;
    private String host;
    private String protocol;
    private Integer port;
    private String user;
    private String password;
    private String database;
}
