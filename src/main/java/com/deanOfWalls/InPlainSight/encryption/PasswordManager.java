package com.deanOfWalls.InPlainSight.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {

    //this method will hash a password using SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        //create a MessageDigest intance for SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        //perform hash computation on password's bytes
        byte[] encodedhash = digest.digest(password.getBytes());
        //convert byte array of the hash into a hexadecimal string
        return bytesToHex(encodedhash);
    }

    //helper method to convert a byte array into a hexadecimal string
    private static String bytesToHex(byte[] hash) {
        //StringBuilder to construct the hexadecimal string
        StringBuilder hexString = new StringBuilder(2 * hash.length);

        //iterate over each byte in the array
        for (int i = 0; i < hash.length; i++) {
            //convert each byte to a hexadecimal value
            String hex = Integer.toHexString(0xff & hash[i]);

            //if hexadecimal string is a single char, prepend with a '0'
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex); //append the hex string to the StringBuilder
        }
        //return the complete hexadecimal string
        return hexString.toString();
    }
}
