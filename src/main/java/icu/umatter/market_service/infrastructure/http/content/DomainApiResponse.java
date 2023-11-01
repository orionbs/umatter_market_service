package icu.umatter.market_service.infrastructure.http.content;

import lombok.Data;

@Data
public class DomainApiResponse {
    private String identifier;
    private String name;
    private String description;
}
