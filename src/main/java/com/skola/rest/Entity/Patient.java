package com.skola.rest.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

    private Long patientId;

    private String firstName;

    private String lastName;
}
