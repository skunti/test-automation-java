package com.ambient.api.models;

import lombok.Data;
import java.util.List;

@Data
public class Appointments {
    private List<Item> items;
    private Meta meta;
    private Status status;
}

