package com.deanOfWalls.InPlainSight.encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

public class ImageEncryptor {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }





}
