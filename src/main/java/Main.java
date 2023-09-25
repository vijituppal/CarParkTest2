import com.tds.carpark.service.impl.BasicCarPark;
import com.tds.carpark.CarParkManager;

import java.util.Scanner;

public class Main {
    /**
     * Quick and dirty front end to allow interaction
     * example commands:
     * add ABC123
     * remove ABC123
     */
    public static void main(String[] args) {
        CarParkManager manager = new CarParkManager(new BasicCarPark());
        Scanner input = new Scanner(System.in);
        System.out.println("Use command 'add' or 'remove' followed by vehicle registration");

        while (true) {
            String command = input.next();
            if ("add".equals(command)) {
                manager.addVehicle(input.next());
            } else if ("remove".equals(command)) {
                manager.removeVehicle(input.next());
            }
        }
    }
}
