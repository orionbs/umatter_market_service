package icu.umatter.market_service.infrastructure.http.adapter;

import icu.umatter.market_service.application.port.InsertDomainPort;
import icu.umatter.market_service.infrastructure.database.repository.DomainDatabaseRepository;
import icu.umatter.market_service.infrastructure.http.content.DomainApiRequest;
import icu.umatter.market_service.infrastructure.http.content.DomainApiResponse;
import icu.umatter.market_service.infrastructure.http.contract.DomainApiContract;
import icu.umatter.market_service.infrastructure.http.mapper.DomainApiMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DomainApiAdapter implements DomainApiContract {

    private final InsertDomainPort insertDomainPort;
    private final DomainDatabaseRepository domainDatabaseRepository;

    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<DomainApiResponse> post(Mono<DomainApiRequest> domainApiRequestMono) {
        return domainApiRequestMono
                .map(DomainApiMapper::toDomain)
                .flatMap(insertDomainPort::insertDomain)
                .map(DomainApiMapper::toDomainApiResponse);
    }
}
