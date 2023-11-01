package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractDatabaseEntity {
    private Employment employment;
    private BigDecimal durationPerWeek;

    public enum Employment {
        FULL_TIME,
        PART_TIME,
        ZERO_HOUR,
        CASUAL,
        FREELANCE,
        INTERNSHIP,
        APPRENTICE
    }
}
