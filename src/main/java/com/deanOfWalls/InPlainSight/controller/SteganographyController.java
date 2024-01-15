package com.yourpackage.controller;

import com.yourpackage.service.SteganographyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/steganography")
public class SteganographyController {

    @Autowired
    private SteganographyService steganographyService;

    @PostMapping("/encode")
    public ResponseEntity<?> encodeFiles(MultipartFile decoyImage, MultipartFile fileToHide, String password) {
        // TODO: Call steganographyService to encode the files
        return ResponseEntity.ok().body("Encoded file");
    }

    @PostMapping("/extract")
    public ResponseEntity<?> extractFiles(MultipartFile stegoImage, String password) {
        // TODO: Call steganographyService to extract the files
        return ResponseEntity.ok().body("Extracted file");
    }
}
