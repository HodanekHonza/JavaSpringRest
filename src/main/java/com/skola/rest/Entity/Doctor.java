package com.skola.rest.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    private Long doctorId;

    private String firstName;

    private String lastName;

    private String specialty;

    private String phoneNumber;

    private String email;
}