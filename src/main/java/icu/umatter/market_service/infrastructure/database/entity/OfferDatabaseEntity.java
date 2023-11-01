package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class OfferDatabaseEntity extends AuditDatabaseEntity {
    @Id
    private Long id;
    private String name;
    private String synopsis;
    private UUID publishedBy;
    private Timestamp publishedDate;
}
