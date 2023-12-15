package com.deanOfWalls.whiteboard;

import com.deanOfWalls.InPlainSight.encryption.ImageEncryptor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageEncryptorTest {

    public static void main(String[] args) throws Exception {
        String key = "your-encryption-key"; // Ensure this is the correct size for AES
        String imagePath = "path/to/your/image.jpg";

        // Convert image to byte array
        byte[] originalImageData = imageToByteArray(imagePath);

        // Encrypt the image data
        byte[] encryptedData = ImageEncryptor.encryptImage(originalImageData, key);

        // Decrypt the image data
        byte[] decryptedData = ImageEncryptor.decryptImage(encryptedData, key);

        // Optional: Save decrypted data to a file for visual comparison
        byteArrayToImage(decryptedData, "path/to/decrypted/image.jpg");

        // Compare original and decrypted data
        boolean isEqual = java.util.Arrays.equals(originalImageData, decryptedData);
        System.out.println("Is original image equal to decrypted image? " + isEqual);
    }

    private static byte[] imageToByteArray(String imagePath) throws IOException {
        BufferedImage bImage = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos);
        return bos.toByteArray();
    }

    private static void byteArrayToImage(byte[] imageData, String outputPath) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
        File outputfile = new File(outputPath);
        ImageIO.write(img, "jpg", outputfile);
    }
}
