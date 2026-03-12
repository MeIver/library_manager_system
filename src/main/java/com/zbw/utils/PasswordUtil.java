package com.zbw.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class PasswordUtil {

    private static final String SALT_PREFIX = "library_system_";
    
    public static String encrypt(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }
        try {
            String saltedPassword = SALT_PREFIX + password;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(saltedPassword.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }
    
    public static boolean verify(String plainPassword, String encryptedPassword) {
        if (plainPassword == null || encryptedPassword == null) {
            return false;
        }
        String encrypted = encrypt(plainPassword);
        return encrypted.equals(encryptedPassword);
    }
    
    public static String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
