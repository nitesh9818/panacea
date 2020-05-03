package com.assignment.api.geospatial.controller;

import com.assignment.api.geospatial.model.GeoCoordinate;
import com.assignment.api.geospatial.model.GeoSpatialInput;
import com.assignment.api.geospatial.model.InputGeoCoordinate;
import com.assignment.api.geospatial.service.GeoCoordinateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${spring.data.rest.base-path}")
public class GeoSpatialController {

    private GeoCoordinateService geoCoordinateService;

    public GeoSpatialController(GeoCoordinateService geoCoordinateService){
        this.geoCoordinateService = geoCoordinateService;
    }

    @RequestMapping(value = "/calculate/distance", method = RequestMethod.GET)
    public ResponseEntity<GeoCoordinate> distanceBetweenCoordinates(@RequestBody InputGeoCoordinate inputGeoCoordinate){
        return new ResponseEntity<>(geoCoordinateService.calculateDistance(inputGeoCoordinate), HttpStatus.OK);
    }

    @RequestMapping(value = "/latLon/LieWithInReferencePoint", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<GeoCoordinate>>> latLonLieWithInReferencePoint(@RequestBody GeoSpatialInput geoSpatialInput){
        Map<String, List<GeoCoordinate>> listMap = new HashMap<>();
        listMap.put("result", geoCoordinateService.latLonLieWithInReferencePoint(geoSpatialInput));
        return new ResponseEntity<>(listMap, HttpStatus.OK);
    }

    @RequestMapping(value = "/sort/latLonFromReferencePoint", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<GeoCoordinate>>> sortListOfLatLonFromReferencePoint(@RequestBody GeoSpatialInput geoSpatialInput){
        Map<String, List<GeoCoordinate>> listMap = new HashMap<>();
        listMap.put("sortedArray", geoCoordinateService.sortListOfLatLonFromReferencePoint(geoSpatialInput));
        return new ResponseEntity<>(listMap, HttpStatus.OK);
    }

}
