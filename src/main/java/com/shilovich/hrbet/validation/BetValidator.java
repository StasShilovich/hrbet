package com.shilovich.hrbet.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetValidator {
    private static final String BET_INFO_REGEX = "Ratio\\{raceId=\\d{1,7}, horseId=\\d{1,7}, typeId=\\d{1,7}, ratio=\\d+.\\d+}";

    private BetValidator() {
    }

    public static boolean isInfoValid(String info) {
        Pattern pattern = Pattern.compile(BET_INFO_REGEX);
        Matcher matcher = pattern.matcher(info);
        return matcher.matches();
    }
}