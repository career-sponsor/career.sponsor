package com.career.sponsor.api.company.bulk;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CompanyBulkDTO {

    private String name;
    private String city;
    private String county;
    private String type;
    private String route;
}
