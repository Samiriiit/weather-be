package com.sapient.sde.weather_predictor.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CryptoUtils {

    private static final String ALGO = "AES";

    public static String decrypt(String encryptedData, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO); // e.g., "AES"
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error decrypting data: " + e.getMessage(), e);
        }
    }

    public static String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }
}
