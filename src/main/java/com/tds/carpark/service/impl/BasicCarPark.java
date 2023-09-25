package com.tds.carpark.service.impl;

import com.tds.carpark.domain.Vehicle;
import com.tds.carpark.exceptions.NoAvailableSpacesException;
import com.tds.carpark.exceptions.VehicleAlreadyParkedException;
import com.tds.carpark.exceptions.VehicleNotFoundException;
import com.tds.carpark.service.CarParkService;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class BasicCarPark implements CarParkService {
    // Using static map to simulate database backing
    private static final Map<Vehicle, LocalDateTime> spaces = new ConcurrentHashMap<>();
    private static final int capacity = 100;
    @Getter
    private static final double price = 2.0;

    public int getAvailableSpaces() {
        return capacity - spaces.size();
    }

    public void addVehicle(Vehicle vehicle, LocalDateTime arrivalTime) throws NoAvailableSpacesException, VehicleAlreadyParkedException {
        if (getAvailableSpaces() <= 0) {
            throw new NoAvailableSpacesException();
        }
        if (hasVehicle(vehicle)) {
            throw new VehicleAlreadyParkedException();
        }

        CompletableFuture<LocalDateTime> localDateTimeCompletableFuture = CompletableFuture.supplyAsync(() -> spaces.put(vehicle, arrivalTime));
        try {
            localDateTimeCompletableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasVehicle(Vehicle vehicle) {
        return spaces.containsKey(vehicle);
    }

    public LocalDateTime getArrivalTime(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        return spaces.get(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) throws VehicleNotFoundException {
        if (!hasVehicle(vehicle)) {
            throw new VehicleNotFoundException();
        }

        spaces.remove(vehicle);
    }

    public void empty() {
        spaces.clear();
    }
}
