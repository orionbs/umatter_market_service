package icu.umatter.market_service.application.port;

import icu.umatter.market_service.domain.model.Domain;
import reactor.core.publisher.Mono;

public interface InsertDomainPort {

    Mono<Domain> insertDomain(Domain domain);
}
