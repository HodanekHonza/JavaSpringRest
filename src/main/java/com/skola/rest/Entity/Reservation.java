package com.skola.rest.Entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

    private Long reservationId;

    private Long doctorId;

    private Long patientId;

    private Long hallId;

    private LocalDate reservationDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String description;

    private String status;
}
