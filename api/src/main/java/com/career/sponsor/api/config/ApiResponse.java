package com.career.sponsor.api.config;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Object content;
    private Pageable pageable;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pageable {
        private int size;
        private int pageNumber;
        private int offset;
    }
}