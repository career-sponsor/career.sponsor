package com.career.sponsor.api.company.controller;

import com.career.sponsor.api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CompanyController {

    @GetMapping("/api/v1/companies/search")
    public ResponseEntity<ApiResponse> search() {
        List<CompanySearchResponse> companies = createCompanyList();

        ApiResponse response = ApiResponse.builder()
                .content(companies)
                .pageable(ApiResponse.Pageable.builder()
                        .size(10)
                        .pageNumber(0)
                        .offset(0)
                        .build())
                .build();

        return ResponseEntity.ok().body(response);
    }

    private List<CompanySearchResponse> createCompanyList() {
        List<CompanySearchResponse> companies = new ArrayList<>();
        companies.add(createCompany("McMullan Shellfish", "Ballymena", "Co Antrim", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("RSS EXPRESS LTD T/A Ledbury Fuel Service Station", "Ledbury", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("(IECC Care) Independent Excel Care Consortium Limited", "Colchester", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("*ABOUTCARE HASTINGS LTD", "East Sussex", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("???£ ESS LTD", "Manchester", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("@ Architect UK Ltd", "West Horndon", "Essex", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("@ Home Accommodation Services Ltd", "London", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("@ Home Accommodation Services Ltd", "London", "NaN", "Temporary Worker (A rating)", "Creative Worker"));
        companies.add(createCompany("@ Ur Eaz Ltd", "High Wycombe", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("@@@ FILER LIMITED", "Bolton", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("[AI] INFINITI LIMITED", "Croydon", "England", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("`Brunswick Stores Limited", "Leamington Spa", "Warwickshire", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("+Plus Care Ltd", "Manchester", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("0022IT Ltd", "Farnham", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("0022Media Ltd", "Brighton", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("0022Media Ltd", "Brighton", "NaN", "Temporary Worker (A rating)", "Creative Worker"));
        companies.add(createCompany("003 Ltd", "Hounslow", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("0086 Ltd", "London", "NaN", "Worker (A rating)", "Skilled Worker"));
        companies.add(createCompany("00Nation Limited", "London", "London", "Worker (A rating)", "Global Business Mobility: Senior or Specialist Worker"));
        companies.add(createCompany("00Nation Limited", "London", "London", "Worker (A rating)", "Skilled Worker"));
        return companies;
    }

    private static CompanySearchResponse createCompany(String name, String city, String county, String type, String route) {
        return CompanySearchResponse.builder()
                .name(name)
                .city(city)
                .county(county)
                .type(type)
                .route(route)
                .build();
    }
}
