package com.example.task_manager_app.common.service;

import org.bouncycastle.crypto.generators.SCrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class AesUtil {
    private static final String ALGO = "AES/CTR/NoPadding";

    private static byte[] deriveKey(String secretKey, String salt, int length) {
        return SCrypt.generate(
                secretKey.getBytes(),
                salt.getBytes(),
                16384,
                8,
                1,
                length
        );
    }

    public static byte[] generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }

    public static byte[] encrypt(byte[] buffer, String secretKey, String salt, byte[] iv) throws Exception {

        if (iv == null) {
            iv = generateIv();
        }

        byte[] key = deriveKey(secretKey, salt, 32);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

        byte[] encryptedData = cipher.doFinal(buffer);

        byte[] result = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encryptedData, 0, result, iv.length, encryptedData.length);

        return result;
    }

    public static byte[] decrypt(byte[] encrypted, String secretKey, String salt) throws Exception {

        byte[] iv = Arrays.copyOfRange(encrypted, 0, 16);
        byte[] ciphertext = Arrays.copyOfRange(encrypted, 16, encrypted.length);

        byte[] key = deriveKey(secretKey, salt, 32);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));

        return cipher.doFinal(ciphertext);
    }

}
