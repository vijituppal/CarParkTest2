package com.tds.carpark.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
    public static int getHoursBetween(LocalDateTime arrival, LocalDateTime exit) {
        long differenceInMillis = timeInMillis(exit) - timeInMillis(arrival);
        double differenceInHours = (double) differenceInMillis / (1000 * 60 * 60);
        // round up to match real world car park pricing
        return (int) Math.ceil(differenceInHours);
    }

    private static long timeInMillis(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
