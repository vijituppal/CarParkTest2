package com.tds.carpark.service;

import com.tds.carpark.domain.Vehicle;
import com.tds.carpark.exceptions.NoAvailableSpacesException;
import com.tds.carpark.exceptions.VehicleAlreadyParkedException;
import com.tds.carpark.exceptions.VehicleNotFoundException;

import java.time.LocalDateTime;

public interface CarParkService {
    int getAvailableSpaces();

    void addVehicle(Vehicle vehicle, LocalDateTime arrivalTime) throws NoAvailableSpacesException, VehicleAlreadyParkedException;

    boolean hasVehicle(Vehicle vehicle);

    LocalDateTime getArrivalTime(Vehicle vehicle) throws VehicleNotFoundException;

    void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException;
}
