package com.deanOfWalls.InPlainSight.controller;

import com.deanOfWalls.InPlainSight.service.SteganographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/steganography")
public class SteganographyController {

    @Autowired
    private SteganographyService steganographyService;

    @Value("${upload.tempDirPath}") // Assuming you have a property defined for the temp directory path
    private String tempDirPath;

    @PostMapping("/encode")
    public ResponseEntity<byte[]> encodeFiles(
            @RequestParam("decoyImage") MultipartFile decoyImage,
            @RequestParam("fileToHide") MultipartFile fileToHide,
            @RequestParam("password") String password
    ) throws IOException, InterruptedException {
        byte[] encodedData = steganographyService.encodeFiles(decoyImage, fileToHide, password, tempDirPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "encoded_image.png");

        return new ResponseEntity<>(encodedData, headers, HttpStatus.OK);
    }

    @PostMapping("/extract")
    public ResponseEntity<byte[]> extractFiles(@RequestParam("stegoImage") MultipartFile stegoImage, @RequestParam("password") String password) throws IOException, InterruptedException {
        byte[] extractedData = steganographyService.decodeFiles(stegoImage.getBytes(), password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "extracted_image.png");

        return new ResponseEntity<>(extractedData, headers, HttpStatus.OK);
    }
}
