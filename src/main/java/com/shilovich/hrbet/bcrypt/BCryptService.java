package com.shilovich.hrbet.bcrypt;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptService {
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public static boolean verifyPassword(String hashPassword, String verifiablePassword) {
        BCrypt.Result verify = BCrypt.verifyer().verify(hashPassword.toCharArray(), verifiablePassword);
        return verify.verified;
    }
}
