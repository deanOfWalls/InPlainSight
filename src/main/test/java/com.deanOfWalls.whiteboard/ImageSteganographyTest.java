package com.deanOfWalls.whiteboard;

import com.deanOfWalls.InPlainSight.steganography.ImageSteganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageSteganographyTest {

    public static void main(String[] args) {
        try {
            // Load the decoy image
            BufferedImage decoyImage = loadImage("/home/dean/Dev/InPlainSight/decoyDog.png");

            // Load the secret image and convert it to a byte array
            BufferedImage secretImage = loadImage("/home/dean/Dev/InPlainSight/secretCat.png");
            byte[] secretData = imageToByteArray(secretImage);

            // Embed the secret data into the decoy image
            BufferedImage stegoImage = ImageSteganography.embedSecretData(decoyImage, secretData);

            // Save the stego image
            saveImage(stegoImage, "/home/dean/Dev/InPlainSight/stegoImage.png");

            // Extract the secret data from the stego image
            byte[] extractedData = ImageSteganography.extractSecretData(stegoImage, secretData.length);

            // Compare the original secret data with the extracted data
            boolean isEqual = java.util.Arrays.equals(secretData, extractedData);
            System.out.println("Is extracted data equal to original secret data? " + isEqual);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage loadImage(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        System.out.println("Loaded image from " + path + " - Type: " + image.getType() + ", Width: " + image.getWidth() + ", Height: " + image.getHeight());
        return image;
    }

    private static void saveImage(BufferedImage image, String path) throws IOException {
        ImageIO.write(image, "png", new File(path));
    }

    private static byte[] imageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        return bos.toByteArray();
    }
}
