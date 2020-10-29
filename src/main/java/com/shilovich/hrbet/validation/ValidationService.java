package com.shilovich.hrbet.validation;

public class ValidationService {
    public static boolean isValidUserName(String userName) {
        // TODO: 15.10.2020
        return userName.length() > 3;
    }

    public static boolean isValidUserSurname(String userSurname) {
        // TODO: 15.10.2020
        return userSurname.length() > 3;
    }

    public static boolean isValidUserPassword(String password) {
        // TODO: 15.10.2020
        return password.length() > 3;
    }

    public static boolean isValidUserEmail(String email) {
        // TODO: 15.10.2020
        return email.contains("@");
    }
}