package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(schema = "PUBLIC", name = "DOMAIN")
public class DomainDatabaseEntity extends AuditDatabaseEntity {
    @Id
    @Column(value = "ID")
    private Long id;
    @Column(value = "NAME")
    private String name;
    @Column(value = "DESCRIPTION")
    private String description;
}
