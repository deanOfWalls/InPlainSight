package com.deanOfWalls.whiteboard;

import com.deanOfWalls.InPlainSight.steganography.ImageSteganography;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSteganographyExtractionTest {

    public static void main(String[] args) {
        try {
            // Load the stego image
            BufferedImage stegoImage = ImageIO.read(new File("/home/dean/Dev/InPlainSight/stegoImage.png"));

            // The size of the secret data needs to be known or estimated
            int secretDataSize = estimateSecretDataSize(); // Implement this method based on your knowledge of the secret image

            // Extract the secret data from the stego image
            byte[] extractedData = ImageSteganography.extractSecretData(stegoImage, secretDataSize);

            // Convert the extracted data back to an image
            BufferedImage extractedImage = ImageSteganography.byteArrayToImage(extractedData);

            // Save or display the extracted image
            ImageIO.write(extractedImage, "png", new File("/home/dean/Dev/InPlainSight/extractedSecretImage.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int estimateSecretDataSize() {
        // Estimate the size of the secret data.
        // This could be a fixed value, or you could implement a method to calculate it.
        // For example, if you know the dimensions and type of the secret image, you can estimate its size.
        return 10260; // Replace with the actual estimated size
    }
}
