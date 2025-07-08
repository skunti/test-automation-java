package com.ambient.ui.models;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private String patientName;
    private String startTime;
    private String endTime;
    private String notes;
}
