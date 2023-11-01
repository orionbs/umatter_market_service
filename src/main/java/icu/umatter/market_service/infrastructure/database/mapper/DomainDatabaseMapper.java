package icu.umatter.market_service.infrastructure.database.mapper;

import icu.umatter.market_service.domain.model.Domain;
import icu.umatter.market_service.infrastructure.database.entity.DomainDatabaseEntity;

import java.util.Optional;

public class DomainDatabaseMapper {
    public static DomainDatabaseEntity toDomainDatabaseEntity(Domain domain) {
        DomainDatabaseEntity domainDatabaseEntity = new DomainDatabaseEntity();
        Optional.of(domain)
                .map(Domain::getIdentifier)
                .map(Long::parseLong)
                .ifPresent(domainDatabaseEntity::setId);
        Optional.of(domain)
                .map(Domain::getName)
                .map(String::trim)
                .ifPresent(domainDatabaseEntity::setName);
        Optional.of(domain)
                .map(Domain::getDescription)
                .map(String::trim)
                .ifPresent(domainDatabaseEntity::setDescription);
        return domainDatabaseEntity;
    }

    public static Domain toDomain(DomainDatabaseEntity domainDatabaseEntity) {
        Domain domain = new Domain();
        Optional.of(domainDatabaseEntity)
                .map(DomainDatabaseEntity::getId)
                .map(String::valueOf)
                .ifPresent(domain::setIdentifier);
        Optional.of(domainDatabaseEntity)
                .map(DomainDatabaseEntity::getName)
                .map(String::trim)
                .ifPresent(domain::setName);
        Optional.of(domainDatabaseEntity)
                .map(DomainDatabaseEntity::getDescription)
                .map(String::trim)
                .ifPresent(domain::setDescription);
        return domain;
    }
}
