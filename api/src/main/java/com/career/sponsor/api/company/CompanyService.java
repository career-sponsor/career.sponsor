package com.career.sponsor.api.company;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CompanyService {

    private final RestHighLevelClient client;

    public CompanyService(RestHighLevelClient client) {
        this.client = client;
    }

    public Page<CompanySearchResponse> search(String index, String fieldName, String fieldValue, Pageable pageable) {
        log.info("index={}, fieldName={}, fieldValue={}", index, fieldName, fieldValue);
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery(fieldName, fieldValue));

        int from = pageable.getPageNumber(); // set start point
        int size = pageable.getPageSize(); // number of document per page
        log.info("page={}", pageable);
        builder.from(from);
        builder.size(size);

        request.source(builder);
        log.info("request={}", request);

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            log.info("response={}", response);
            List<CompanySearchResponse> companies = new ArrayList<>();

            response.getHits().forEach(hit -> {
                log.info("hit={}", hit);
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                CompanySearchResponse company = CompanySearchResponse.builder()
                        .county((String) sourceAsMap.get("county"))
                        .route((String) sourceAsMap.get("route"))
                        .name((String) sourceAsMap.get("name"))
                        .city((String) sourceAsMap.get("city"))
                        .type((String) sourceAsMap.get("type"))
                        .build();
                log.info("company={}", company);
                companies.add(company);

            });
            return new PageImpl<CompanySearchResponse>(companies, pageable, response.getHits().getTotalHits().value);
        } catch (IOException e) {
            log.error("error while searching company: ", e);
            throw new RuntimeException(e);
        }
    }
}
