package icu.umatter.market_service.infrastructure.http.content;

import lombok.Data;

@Data
public class DomainApiRequest {
    private String name;
    private String description;
}
