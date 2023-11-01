package icu.umatter.market_service.infrastructure.database.repository;

import icu.umatter.market_service.infrastructure.database.entity.OfferDatabaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface OfferDatabaseRepository extends R2dbcRepository<OfferDatabaseEntity, Long> {
}
