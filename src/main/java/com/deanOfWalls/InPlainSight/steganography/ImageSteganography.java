package com.deanOfWalls.InPlainSight.steganography;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import org.springframework.web.multipart.MultipartFile;

public class ImageSteganography {

    // Embeds secret data into a decoy image using the LSB technique
    public static BufferedImage embedSecretData(BufferedImage decoyImage, byte[] secretData) {
        // Ensure the decoy image can hold the secret data
        int dataCapacity = decoyImage.getWidth() * decoyImage.getHeight();
        // Increase the threshold from 8 to 16
        if (secretData.length * 16 > dataCapacity) {
            throw new IllegalArgumentException("Secret data is too large for the given image.");
        }

        BufferedImage stegoImage = new BufferedImage(decoyImage.getWidth(), decoyImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int dataIndex = 0;
        int bitIndex = 0;

        for (int y = 0; y < decoyImage.getHeight(); y++) {
            for (int x = 0; x < decoyImage.getWidth(); x++) {
                int pixel = decoyImage.getRGB(x, y);
                int lsb = dataIndex < secretData.length ? (secretData[dataIndex] >> (7 - bitIndex)) & 1 : 0;
                pixel = (pixel & 0xFFFFFFFE) | lsb;
                stegoImage.setRGB(x, y, pixel);

                bitIndex++;
                if (bitIndex == 8) {
                    bitIndex = 0;
                    dataIndex++;
                }
            }
        }

        return stegoImage;
    }

    // Extracts secret data from a stego image
    public static byte[] extractSecretData(BufferedImage stegoImage, int dataLength) {
        ByteBuffer buffer = ByteBuffer.allocate(dataLength);
        int dataIndex = 0;
        int bitIndex = 0;
        byte currentByte = 0;

        for (int y = 0; y < stegoImage.getHeight(); y++) {
            for (int x = 0; x < stegoImage.getWidth(); x++) {
                int pixel = stegoImage.getRGB(x, y);
                int lsb = pixel & 1;
                currentByte = (byte) ((currentByte << 1) | lsb);

                bitIndex++;
                if (bitIndex == 8) {
                    buffer.put(currentByte);
                    currentByte = 0;
                    bitIndex = 0;
                    dataIndex++;
                    if (dataIndex >= dataLength) {
                        return buffer.array();
                    }
                }
            }
        }

        return buffer.array();
    }

    // Converts a MultipartFile to a BufferedImage
    public static BufferedImage convertToBufferedImage(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        return ImageIO.read(inputStream);
    }

    // Converts an image to a byte array
    public static byte[] imageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        return bos.toByteArray();
    }

    // Converts a byte array back to an image
    public static BufferedImage byteArrayToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return ImageIO.read(bis);
    }
}
