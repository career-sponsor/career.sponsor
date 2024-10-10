package com.career.sponsor.api.company.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CompanyLocalCsvReader implements CompanyCsvReader{

    @Override
    public Iterable<Map<String, Object>> read() {
        List<Map<String, Object>> records = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("companies.csv").getInputStream()))) {
            String[] header = reader.readNext();
            String[] line;
            while ((line = reader.readNext()) != null) {
                Map<String, Object> record = new HashMap<>();
                for (int i = 0; i < header.length; i++) {
                    record.put(header[i], line[i]);
                }
                log.info("record={}", record);
                records.add(record);
            }
            return records;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
