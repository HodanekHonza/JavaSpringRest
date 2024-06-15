package com.skola.rest.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperatingRoom {

    private Long hallId;

    private String hallName;

    private String location;
}
