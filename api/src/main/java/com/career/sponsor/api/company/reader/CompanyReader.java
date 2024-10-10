package com.career.sponsor.api.company.reader;

import java.util.Map;

public interface CompanyReader {

    Iterable<Map<String, Object>> read();
}
