package icu.umatter.market_service.infrastructure.database.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class AuditDatabaseEntity {
    @CreatedBy
    @Column(value = "CREATED_BY")
    private UUID createdBy;
    @CreatedDate
    @Column(value = "CREATED_DATE")
    private Timestamp createdDate;
    @LastModifiedBy
    @Column(value = "LAST_MODIFIED_BY")
    private UUID lastModifiedBy;
    @LastModifiedDate
    @Column(value = "LAST_MODIFIED_DATE")
    private Timestamp lastModifiedDate;
}
