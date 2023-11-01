package icu.umatter.market_service.infrastructure.database.property;

import lombok.Data;

@Data
public class VersionDatabaseProperty {
    private boolean enabled;
    private String changeLog;
}
