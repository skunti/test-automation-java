package com.ambient.api.models;

import lombok.Data;

@Data
public class Item {
    private String id;
    private String etag;
    private String patient_name;
    private String scheduled_start;
    private String scheduled_end;
    private String notes;
}
