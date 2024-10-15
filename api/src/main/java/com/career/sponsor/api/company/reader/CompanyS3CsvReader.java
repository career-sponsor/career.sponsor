package com.career.sponsor.api.company.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CompanyS3CsvReader implements CompanyCsvReader {

    private final S3Client s3Client;
    private final String bucketName;
    private final String objectKey;

    public CompanyS3CsvReader(S3Client s3Client) {
        this.s3Client = s3Client;
        this.bucketName = "career-sponsor";
        this.objectKey = "csv/companies.csv";
    }

    @Override
    public Iterable<Map<String, Object>> read() {
        List<Map<String, Object>> recordsList = new ArrayList<>();

        try {
            // Download the CSV file from S3
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            ResponseInputStream<?> s3ObjectInputStream = s3Client.getObject(getObjectRequest);

            // Read and parse the CSV file
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3ObjectInputStream));
                 CSVReader csvReader = new CSVReader(reader)) {

                // Read the header row
                String[] headers = csvReader.readNext();
                if (headers == null) {
                    throw new RuntimeException("CSV file is empty or missing header row.");
                }

                // Iterate over each row in the CSV file
                String[] row;
                while ((row = csvReader.readNext()) != null) {
                    Map<String, Object> rowMap = new HashMap<>();
                    for (int i = 0; i < headers.length; i++) {
                        rowMap.put(headers[i], row.length > i ? row[i] : null);
                    }
                    log.info("row={}", rowMap);
                    recordsList.add(rowMap);
                }
            } catch (CsvValidationException e) {
                e.printStackTrace();
                throw new RuntimeException("Error while parsing the CSV file", e);
            }
        } catch (S3Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download or parse the CSV file from S3", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading the CSV file", e);
        }

        return recordsList;
    }
}
