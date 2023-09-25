package com.tds.carpark.exceptions;

public class VehicleNotFoundException extends Throwable {
    public VehicleNotFoundException() {
        super("The requested vehicle is not parked here");
    }
}
