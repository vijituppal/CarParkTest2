package com.tds.carpark;

import com.tds.carpark.domain.Vehicle;
import com.tds.carpark.exceptions.NoAvailableSpacesException;
import com.tds.carpark.exceptions.VehicleAlreadyParkedException;
import com.tds.carpark.service.impl.BasicCarPark;
import com.tds.carpark.utils.DateTimeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class CarParkManagerTest {

    private static CarParkManager manager;

    private static BasicCarPark carPark = new BasicCarPark();

    @BeforeAll
    private static void setUp() {
        manager = new CarParkManager(carPark);
    }

    @BeforeEach
    private void clearDown() {
        carPark.empty();
    }

    @Test
    public void testCarParkCapacity() {
        Assertions.assertEquals(100, carPark.getAvailableSpaces());
    }

    @Test
    public void testCantAddToFullCarPark() {
        fillCarPark(0);
        Assertions.assertEquals(new NoAvailableSpacesException().getMessage(), manager.addVehicle("ABC123"));
    }

    @Test
    public void testAddToLastAvailableSpace() {
        fillCarPark(1);
        Assertions.assertEquals(1, carPark.getAvailableSpaces());

        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(0, carPark.getAvailableSpaces());
    }

    @Test
    public void testAdd2ToLastAvailableSpace() {
        fillCarPark(1);
        Assertions.assertEquals(1, carPark.getAvailableSpaces());

        String reg1 = "ABC123";
        String reg2 = "ABC456";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(new NoAvailableSpacesException().getMessage(), manager.addVehicle(reg2));
        Assertions.assertEquals(0, carPark.getAvailableSpaces());
    }

    @Test
    public void testAddToEmptyCarPark() {
        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(99, carPark.getAvailableSpaces());
    }

    @Test
    public void testRemoveVehicleNotAdded() {
        Assertions.assertEquals(0, manager.removeVehicle("ABC123"));
    }

    @Test
    public void testRemoveJustAdded() throws InterruptedException {
        String reg1 = "ABC123";
        manager.addVehicle(reg1);
        // wait to avoid issues caused by quick processing
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(2.0, manager.removeVehicle(reg1));
        Assertions.assertEquals(100, carPark.getAvailableSpaces());
    }

    @Test
    public void testRemoveAfter2Hours() throws NoAvailableSpacesException, VehicleAlreadyParkedException {
        String reg1 = "ABC123";
        // add a few minutes to arrival time to avoid total going over two hours
        carPark.addVehicle(new Vehicle(reg1), DateTimeUtils.now().minusHours(2).plusMinutes(2));
        Assertions.assertEquals(4.0, manager.removeVehicle(reg1));
        Assertions.assertEquals(100, carPark.getAvailableSpaces());
    }

    @Test
    public void testAddDuplicate() {
        String reg1 = "ABC123";
        String confirmationMsg = "Vehicle registration: " + reg1 + " parked at";
        Assertions.assertTrue(manager.addVehicle(reg1).startsWith(confirmationMsg));
        Assertions.assertEquals(99, carPark.getAvailableSpaces());
        Assertions.assertEquals(new VehicleAlreadyParkedException().getMessage(), manager.addVehicle(reg1));
        Assertions.assertEquals(99, carPark.getAvailableSpaces());
    }

    private void fillCarPark(int spacesRemaining) {
        try {
            for (int i = 0; carPark.getAvailableSpaces() > spacesRemaining; i++) {
                carPark.addVehicle(new Vehicle("ABC" + i), DateTimeUtils.now());
            }
        } catch (NoAvailableSpacesException | VehicleAlreadyParkedException e) {
            // do nothing
        }
    }
}
