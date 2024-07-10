package com.career.sponsor.api.company.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanySearchResponse {

    private String name;
    private String city;
    private String county;
    private String type;
    private String route;
}
