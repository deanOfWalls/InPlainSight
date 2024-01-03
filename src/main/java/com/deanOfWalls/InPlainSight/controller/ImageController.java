package com.deanOfWalls.InPlainSight.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ResourceLoader resourceLoader;

    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping
    public ResponseEntity<List<String>> listImages() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static/");
        return ResponseEntity.ok(
                Files.walk(Paths.get(resource.getURI()))
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".png") || path.toString().endsWith(".jpg"))
                        .map(path -> path.getFileName().toString())
                        .collect(Collectors.toList())
        );
    }
}
