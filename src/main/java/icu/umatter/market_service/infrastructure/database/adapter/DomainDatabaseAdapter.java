package icu.umatter.market_service.infrastructure.database.adapter;

import icu.umatter.market_service.application.port.InsertDomainPort;
import icu.umatter.market_service.domain.model.Domain;
import icu.umatter.market_service.infrastructure.database.mapper.DomainDatabaseMapper;
import icu.umatter.market_service.infrastructure.database.repository.DomainDatabaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DomainDatabaseAdapter implements InsertDomainPort {
    private final DomainDatabaseRepository domainDatabaseRepository;

    @Override
    public Mono<Domain> insertDomain(Domain domain) {
        return Mono.just(domain)
                .map(DomainDatabaseMapper::toDomainDatabaseEntity)
                .flatMap(domainDatabaseRepository::save)
                .map(DomainDatabaseMapper::toDomain);
    }


}
