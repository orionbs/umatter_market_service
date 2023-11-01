package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;

@Data
public class LocationDatabaseEntity {
    private String country;
    private String city;
    private Float longitude;
    private Float latitude;
}
