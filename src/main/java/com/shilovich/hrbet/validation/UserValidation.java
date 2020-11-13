package com.shilovich.hrbet.validation;

public class UserValidation {
    public static boolean isValidName(String userName) {
        // TODO: 15.10.2020
        return userName.length() > 3;
    }

    public static boolean isValidSurname(String userSurname) {
        // TODO: 15.10.2020
        return userSurname.length() > 3;
    }

    public static boolean isValidPassword(String password) {
        // TODO: 15.10.2020
        return password.length() > 3;
    }

    public static boolean isValidEmail(String email) {
        // TODO: 15.10.2020
        return email.contains("@");
    }
}