package com.assignment.api.geospatial.service;

import com.assignment.api.geospatial.Utils.GeoDistanceCalculation;
import com.assignment.api.geospatial.model.GeoCoordinate;
import com.assignment.api.geospatial.model.GeoSpatialInput;
import com.assignment.api.geospatial.model.InputGeoCoordinate;

import java.util.List;

public interface GeoCoordinateService {
    GeoCoordinate calculateDistance(InputGeoCoordinate inputGeoCoordinate);
    List<GeoCoordinate> latLonLieWithInReferencePoint(GeoSpatialInput geoSpatialInput);
    List<GeoCoordinate> sortListOfLatLonFromReferencePoint(GeoSpatialInput geoSpatialInput);

    default Double calculateDistance(Double latitude1, Double longitude1, Double latitude2, Double longitude2){
        InputGeoCoordinate buildObject = InputGeoCoordinate.builder()
                .latitude1(latitude1)
                .longitude1(longitude1)
                .latitude2(latitude2)
                .longitude2(longitude2)
                .build();
        return GeoDistanceCalculation.getDistanceFromLatLonInKm(buildObject);
    }
}
