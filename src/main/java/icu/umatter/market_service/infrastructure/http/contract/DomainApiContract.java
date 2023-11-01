package icu.umatter.market_service.infrastructure.http.contract;

import icu.umatter.market_service.infrastructure.http.content.DomainApiRequest;
import icu.umatter.market_service.infrastructure.http.content.DomainApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@RequestMapping(path = "domains")
public interface DomainApiContract {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    Mono<DomainApiResponse> post(
            @RequestBody Mono<DomainApiRequest> domainApiRequestMono
    );

}
