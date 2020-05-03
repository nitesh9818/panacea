package com.assignment.api.geospatial.service.impl;

import com.assignment.api.geospatial.Utils.GeoDistanceCalculation;
import com.assignment.api.geospatial.model.GeoCoordinate;
import com.assignment.api.geospatial.model.GeoSpatialInput;
import com.assignment.api.geospatial.model.InputGeoCoordinate;
import com.assignment.api.geospatial.service.GeoCoordinateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeoCoordinateServiceImpl implements GeoCoordinateService {

    /**
     * @apiNote : calculate distance between the co-ordinates.
     * @return : return GeoCoordinate object in response.
     */
    @Override
    public GeoCoordinate calculateDistance(InputGeoCoordinate inputGeoCoordinate){
        if (inputGeoCoordinate.getLatitude1() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude1 can not be null");
        if (inputGeoCoordinate.getLongitude1() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude1 can not be null");
        if (inputGeoCoordinate.getLatitude2() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Latitude2 can not be null");
        if (inputGeoCoordinate.getLongitude2() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Longitude2 can not be null");

        //Calculate Distance between two coordinates.
        Double fromLatLonInKm = GeoDistanceCalculation.getDistanceFromLatLonInKm(inputGeoCoordinate);
        if (fromLatLonInKm == 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Geo Co-ordinate");

        //Setting distance in geoCoordinate object using lombok builder and return.
        return GeoCoordinate
                .builder()
                .distanceKM(fromLatLonInKm)
                .build();
    }

    /**
     * @apiNote : Calculate the distance from a give reference point and find the co-ordinates which is lies with in the
     * given distance.
     * @return : return list of  GeoCoordinate object in response.
     */
    @Override
    public List<GeoCoordinate> latLonLieWithInReferencePoint(GeoSpatialInput geoSpatialInput){
        if (!Optional.ofNullable(geoSpatialInput.getReferencePoint()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reference point can not be null");

        if (geoSpatialInput.getDistanceKM() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Distance in KM is required");
        if (geoSpatialInput.getArray() == null || geoSpatialInput.getArray().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "List of Array is required");

        //Filter the list of array based on coordinate which lies with in the distance from a reference point.
        return geoSpatialInput.getArray().stream().filter(geoCoordinate -> {
            Double distanceFromLatLonInKm = this.calculateDistance(geoSpatialInput.getReferencePoint().getLatitude(),
                    geoSpatialInput.getReferencePoint().getLongitude(), geoCoordinate.getLatitude(), geoCoordinate.getLongitude());

            return distanceFromLatLonInKm > 0 && distanceFromLatLonInKm <= geoSpatialInput.getDistanceKM();
        }).collect(Collectors.toList());
    }

    /**
     * @apiNote : Calculate the distance from a give reference point and sort the given array on the basis of their distance.
     * @return : return list of GeoCoordinate object in response.
     */
    @Override
    public List<GeoCoordinate> sortListOfLatLonFromReferencePoint(GeoSpatialInput geoSpatialInput){
        if (!Optional.ofNullable(geoSpatialInput.getReferencePoint()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reference point can not be null");

        if (geoSpatialInput.getArray() == null || geoSpatialInput.getArray().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "List of Array is required");

        return geoSpatialInput.getArray().stream().sorted((geoCoordinate, t1) -> {
            Double distanceFromLatLonInKm1 = this.calculateDistance(geoSpatialInput.getReferencePoint().getLatitude(),
                    geoSpatialInput.getReferencePoint().getLongitude(), geoCoordinate.getLatitude(), geoCoordinate.getLongitude());

            Double distanceFromLatLonInKm2 = this.calculateDistance(geoSpatialInput.getReferencePoint().getLatitude(),
                    geoSpatialInput.getReferencePoint().getLongitude(), t1.getLatitude(), t1.getLongitude());
            return distanceFromLatLonInKm1.compareTo(distanceFromLatLonInKm2);
        }).collect(Collectors.toList());
    }

}
