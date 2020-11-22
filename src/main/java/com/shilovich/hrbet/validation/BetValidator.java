package com.shilovich.hrbet.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetValidator {
    private static final String BET_INFO_REGEX = "";

    private BetValidator() {
    }

    public static boolean isInfoValid(String info) {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(info);
        return matcher.matches();
    }
}
