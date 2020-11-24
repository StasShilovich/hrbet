package com.shilovich.hrbet.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {
    private static final String ID_REGEX = "^\\d{1,7}";

    private CommonValidator() {
    }

    public static boolean isIdValid(String raceId) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(raceId);
        return matcher.matches();
    }

    public static boolean isBigDecimalValid(String bigDecimal){
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(bigDecimal);
        return matcher.matches();
    }
}