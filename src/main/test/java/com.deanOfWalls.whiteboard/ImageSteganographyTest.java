package com.deanOfWalls.whiteboard;

import com.deanOfWalls.InPlainSight.steganography.ImageSteganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSteganographyTest {

    public static void main(String[] args) {
        try {
            BufferedImage decoyImage = ImageIO.read(new File("/home/dean/Dev/InPlainSight/decoyDog.png"));
            BufferedImage secretImage = ImageIO.read(new File("/home/dean/Dev/InPlainSight/secretCat.png"));

            byte[] secretData = ImageSteganography.imageToByteArray(secretImage);
            System.out.println("Secret data length: " + secretData.length);

            BufferedImage stegoImage = ImageSteganography.embedSecretData(decoyImage, secretData);
            ImageIO.write(stegoImage, "png", new File("/home/dean/Dev/InPlainSight/stegoImage.png"));

            byte[] extractedData = ImageSteganography.extractSecretData(stegoImage, secretData.length);
            System.out.println("Extracted data length: " + extractedData.length);

            // Save extracted data to a file for examination
            try (FileOutputStream fos = new FileOutputStream("/home/dean/Dev/InPlainSight/extractedData.bin")) {
                fos.write(extractedData);
            }

            BufferedImage extractedImage = ImageSteganography.byteArrayToImage(extractedData);
            System.out.println("Extracted image created successfully: " + (extractedImage != null));

            if (extractedImage != null) {
                ImageIO.write(extractedImage, "png", new File("/home/dean/Dev/InPlainSight/extractedSecretCat.png"));
            }

            // Test with unaltered image data
            BufferedImage testImage = ImageIO.read(new File("/home/dean/Dev/InPlainSight/secretCat.png"));
            byte[] testImageData = ImageSteganography.imageToByteArray(testImage);
            BufferedImage reconstructedTestImage = ImageSteganography.byteArrayToImage(testImageData);
            System.out.println("Reconstructed test image created successfully: " + (reconstructedTestImage != null));

            if (reconstructedTestImage != null) {
                ImageIO.write(reconstructedTestImage, "png", new File("/home/dean/Dev/InPlainSight/reconstructedTestImage.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
