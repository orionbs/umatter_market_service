package icu.umatter.market_service.infrastructure.http.mapper;

import icu.umatter.market_service.domain.model.Domain;
import icu.umatter.market_service.infrastructure.http.content.DomainApiRequest;
import icu.umatter.market_service.infrastructure.http.content.DomainApiResponse;

import java.util.Optional;

public class DomainApiMapper {

    public static Domain toDomain(DomainApiRequest domainApiRequest) {
        Domain domain = new Domain();
        Optional.of(domainApiRequest)
                .map(DomainApiRequest::getName)
                .map(String::trim)
                .ifPresent(domain::setName);
        Optional.of(domainApiRequest)
                .map(DomainApiRequest::getDescription)
                .map(String::trim)
                .ifPresent(domain::setDescription);
        return domain;
    }

    public static DomainApiResponse toDomainApiResponse(Domain domain) {
        DomainApiResponse domainApiResponse = new DomainApiResponse();
        Optional.of(domain)
                .map(Domain::getIdentifier)
                .ifPresent(domainApiResponse::setIdentifier);
        Optional.of(domain)
                .map(Domain::getName)
                .map(String::trim)
                .ifPresent(domainApiResponse::setName);
        Optional.of(domain)
                .map(Domain::getDescription)
                .map(String::trim)
                .ifPresent(domainApiResponse::setDescription);
        return domainApiResponse;
    }
}
