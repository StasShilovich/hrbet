package com.shilovich.hrbet.validation;

public class RaceValidation {
    public static boolean isValidLocation(String location) {
        // TODO: 15.10.2020
        return location.length() > 3;
    }
    public static boolean isValidDateTime(String dateTime) {
        // TODO: 15.10.2020
        return dateTime.length() > 8;
    }
}
