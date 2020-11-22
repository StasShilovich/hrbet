package com.shilovich.hrbet.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptService {
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String hashPassword, String verifiablePassword) {
        BCrypt.Result verify = BCrypt.verifyer().verify(hashPassword.trim().toCharArray(), verifiablePassword.trim());
        return verify.verified;
    }
}
