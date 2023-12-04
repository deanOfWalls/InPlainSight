package com.deanOfWalls.whiteboard;

import com.deanOfWalls.InPlainSight.encryption.PasswordManager;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class PasswordManagerTest {

    @Test
    public void passwordHashTest() throws NoSuchAlgorithmException {
        //given
        String testPassword = "Abc123!";

        //when
        String hashedPassword = PasswordManager.hashPassword(testPassword);

        //then
        System.out.println("Test password was: " + testPassword);
        System.out.println("Hashed password is: " + hashedPassword);
        Assert.assertNotEquals(testPassword, hashedPassword);

    }


}
