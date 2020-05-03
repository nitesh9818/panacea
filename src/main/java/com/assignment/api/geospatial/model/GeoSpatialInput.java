package com.assignment.api.geospatial.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class GeoSpatialInput {

    private GeoCoordinate referencePoint;

    private Double distanceKM;

    private List<GeoCoordinate> array;

}
