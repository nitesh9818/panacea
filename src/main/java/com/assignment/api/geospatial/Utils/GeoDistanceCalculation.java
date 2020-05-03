package com.assignment.api.geospatial.Utils;

import com.assignment.api.geospatial.model.InputGeoCoordinate;

import java.text.DecimalFormat;

public class GeoDistanceCalculation {
    public static Double getDistanceFromLatLonInKm(InputGeoCoordinate coordinate){

        double R = 6371; // Radius of earth in KM.
        double dLat = deg2rad(coordinate.getLatitude2() - coordinate.getLatitude1());
        double dLon = deg2rad(coordinate.getLongitude2() - coordinate.getLongitude1());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(deg2rad(coordinate.getLatitude1())) * Math.cos(deg2rad(coordinate.getLatitude2()))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Double.valueOf(new DecimalFormat("##.##").format(R * c)); //Distance in KM upto two decimal point.

    }

    private static Double deg2rad(double deg){
        return deg * (Math.PI / 100);
    }
}
