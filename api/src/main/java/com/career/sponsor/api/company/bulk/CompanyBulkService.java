package com.career.sponsor.api.company.bulk;


import com.career.sponsor.api.company.reader.CompanyReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CompanyBulkService {

    private static final String INDEX_NAME = "companies";

    private final RestHighLevelClient client;
    private final CompanyReader reader;

    public void bulkInsert() {
        Iterable<Map<String, Object>> records = reader.read();

        BulkRequest bulkRequest = new BulkRequest();

        for (Map<String, Object> record : records) {
            IndexRequest indexRequest = new IndexRequest("companies")
                    .source(record, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }

        // Bulk insert 실행
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.info("Bulk insert had failures: " + bulkResponse.buildFailureMessage());
            } else {
                log.info("Bulk insert completed successfully!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}