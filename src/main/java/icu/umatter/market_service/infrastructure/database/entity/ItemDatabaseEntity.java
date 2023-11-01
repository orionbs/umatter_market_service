package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;

@Data
public class ItemDatabaseEntity {

    private String value;
    private Mastery mastery;

    public enum Mastery {
        LOW,
        MEDIUM,
        HIGH
    }

}
