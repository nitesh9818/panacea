package com.assignment.api.geospatial.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputGeoCoordinate {

    private Double latitude1;

    private Double longitude1;

    private Double latitude2;

    private Double longitude2;
}
