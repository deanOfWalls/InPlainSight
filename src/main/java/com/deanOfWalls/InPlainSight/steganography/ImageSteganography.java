package com.deanOfWalls.InPlainSight.steganography;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;

public class ImageSteganography {

    // Embeds secret data into a decoy image using the LSB technique
    public static BufferedImage embedSecretData(BufferedImage decoyImage, byte[] secretData) {
        // Ensure the decoy image can hold the secret data
        int dataCapacity = decoyImage.getWidth() * decoyImage.getHeight();
        if (secretData.length * 8 > dataCapacity) {
            throw new IllegalArgumentException("Secret data is too large for the given image.");
        }

        // Create a copy of the image to modify
        BufferedImage stegoImage = new BufferedImage(decoyImage.getWidth(), decoyImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = stegoImage.getRaster();
        raster.setRect(decoyImage.getRaster());

        // Embed each bit of the secret data into the LSB of the image pixels
        int dataIndex = 0;
        int bitIndex = 0;
        int numBits = secretData.length * 8;

        for (int y = 0; y < decoyImage.getHeight() && dataIndex < numBits; y++) {
            for (int x = 0; x < decoyImage.getWidth() && dataIndex < numBits; x++) {
                // Debugging output
                System.out.println("Embedding at pixel (" + x + ", " + y + "), dataIndex: " + dataIndex + ", bitIndex: " + bitIndex);

                int pixel = decoyImage.getRGB(x, y);
                int lsb = (secretData[dataIndex / 8] >> (7 - bitIndex)) & 1;
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

        for (int y = 0; y < stegoImage.getHeight() && dataIndex < dataLength * 8; y++) {
            for (int x = 0; x < stegoImage.getWidth() && dataIndex < dataLength * 8; x++) {
                int pixel = stegoImage.getRGB(x, y);
                int lsb = pixel & 1;
                currentByte = (byte) ((currentByte << 1) | lsb);

                bitIndex++;
                if (bitIndex == 8) {
                    if (dataIndex < dataLength) {
                        buffer.put(currentByte);
                    } else {
                        System.out.println("Attempt to overflow buffer at dataIndex: " + dataIndex);
                        break;
                    }
                    currentByte = 0;
                    bitIndex = 0;
                    dataIndex++;
                }
            }
        }

        return buffer.array();
    }
}
