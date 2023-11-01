package icu.umatter.market_service.infrastructure.database.repository;

import icu.umatter.market_service.infrastructure.database.entity.DomainDatabaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface DomainDatabaseRepository extends R2dbcRepository<DomainDatabaseEntity, Long> {
}
