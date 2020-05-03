package com.assignment.api.geospatial.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoCoordinate {

    private Double latitude;

    private Double longitude;

    private Double distanceKM;
}
