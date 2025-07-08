package com.ambient.api.models;

import lombok.Data;

@Data
public class Meta {
    private int total_count;
    private int page_count;
    private int count_per_page;
    private String ctag;
}
