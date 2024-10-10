package com.career.sponsor.api.company;

import com.career.sponsor.api.company.bulk.CompanyBulkService;
import com.career.sponsor.api.config.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CompanyController {

    private static final String INDEX_COMPANY = "companies";
    private final CompanyService companyService;
    private final CompanyBulkService companyBulkService;

    @GetMapping("/api/v1/companies/search")
    public ResponseEntity<ApiResponse> search(
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldValue") String fieldValue,
            Pageable pageable
    ) {
        Page<CompanySearchResponse> companies = companyService.search(INDEX_COMPANY, fieldName, fieldValue, pageable);

        ApiResponse response = ApiResponse.builder()
                .content(companies.getContent())
                .pageable(ApiResponse.Pageable.builder()
                        .size(companies.getSize())
                        .pageNumber(companies.getSize())
                        .offset(companies.getTotalPages())
                        .build())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/api/v1/companies/bulk-insert")
    public ResponseEntity<String> bulkInsertCsv() {
        companyBulkService.bulkInsert();
        return ResponseEntity.ok().build();
    }
}
