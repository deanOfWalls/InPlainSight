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
                                         @RequestParam("secretData") MultipartFile secretDataFile) {
        try {
            BufferedImage decoyImage = ImageSteganography.convertToBufferedImage(decoyImageFile);
            BufferedImage secretImage = ImageSteganography.convertToBufferedImage(secretDataFile);

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

    @PostMapping("/decode")
    public ResponseEntity<?> decodeImage(@RequestParam("stegoImage") MultipartFile stegoImageFile,
                                         @RequestParam("dataLength") int dataLength) {
        try {
            BufferedImage stegoImage = ImageSteganography.convertToBufferedImage(stegoImageFile);
            byte[] extractedData = ImageSteganography.extractSecretData(stegoImage, dataLength);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new ByteArrayResource(extractedData));
        } catch (IOException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to extract data from image: " + e.getMessage());
        }
    }
}
