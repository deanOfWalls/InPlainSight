package com.deanOfWalls.InPlainSight.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class ImageEncryptor {

    //method to encrypt image
    public static byte[] encryptImage(byte[] imageData, String key) throws Exception {
        //convert key to a SecretKeySpec
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");

        //Create cipher instance for AES encryption
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        //Encrypt the image data
        return cipher.doFinal(imageData);
    }

    //Method to decrypt image
    public static byte[] decryptImage(byte[] encryptedData, String key) throws Exception {
        //convert key to a SecretKeySpec
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");

        //Create a cipher instance for AES decryption
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        //decrypt the image data
        return cipher.doFinal(encryptedData);
    }
}
