package com.concord.restservice.encryptor;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes256Encryptor {

    private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static IvParameterSpec ivSpec = new IvParameterSpec(iv);

    public static String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = prepareSecretKeySpec();
            Cipher cipher = getCipherInstance();

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = prepareSecretKeySpec();
            Cipher cipher = getCipherInstance();

            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    private static SecretKeySpec prepareSecretKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String secretKey = "SecretKey1234";
        String salt = "Salt4321";

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    private static Cipher getCipherInstance() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance("AES/CBC/PKCS5PADDING");
    }
}
