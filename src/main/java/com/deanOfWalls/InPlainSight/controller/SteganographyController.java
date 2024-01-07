package com.deanOfWalls.InPlainSight.controller;

import com.deanOfWalls.InPlainSight.steganography.ImageSteganography;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api/steganography")
public class SteganographyController {

    @PostMapping("/encode")
    public ResponseEntity<?> encodeImage(@RequestParam("decoyImage") MultipartFile decoyImageFile,
                                         @RequestParam("secretImage") MultipartFile secretImageFile) {
        try {
            BufferedImage decoyImage = ImageSteganography.convertToBufferedImage(decoyImageFile);
            BufferedImage secretImage = ImageSteganography.convertToBufferedImage(secretImageFile);

            BufferedImage stegoImage = ImageSteganography.embedSecretData(decoyImage, ImageSteganography.imageToByteArray(secretImage));
            byte[] stegoImageData = ImageSteganography.imageToByteArray(stegoImage);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new ByteArrayResource(stegoImageData));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process images: " + e.getMessage());
        }
    }

    @PostMapping("/extract")
    public ResponseEntity<?> extractImage(@RequestParam("stegoImage") MultipartFile stegoImageFile) {
        try {
            BufferedImage stegoImage = ImageSteganography.convertToBufferedImage(stegoImageFile);

            // Replace 'dataLength' with the estimated size of the extracted data
            int dataLength = estimateDataLength(stegoImage);

            byte[] extractedData = ImageSteganography.extractSecretData(stegoImage, dataLength);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new ByteArrayResource(extractedData));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to extract data from image: " + e.getMessage());
        }
    }

    private int estimateDataLength(BufferedImage image) {
        // Implement the logic to estimate the size of the extracted data
        // You can calculate it based on the image dimensions or other factors
        return 0; // Replace with the actual estimated data length
    }
}
