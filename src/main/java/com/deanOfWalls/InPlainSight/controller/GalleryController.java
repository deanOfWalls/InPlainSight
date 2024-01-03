package com.deanOfWalls.InPlainSight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GalleryController {

    private final String imageDirectoryPath = "/src/main/resources/static"; // Adjust this path

    @GetMapping("/gallery")
    public String gallery(@RequestParam(required = false) String directory, Model model) {
        if (directory != null && !directory.isEmpty()) {
            // Assuming directory is a path to the folder containing images
            File folder = new File(directory);
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

            if (files != null) {
                List<String> imageUrls = Arrays.stream(files)
                        .map(file -> imageDirectoryPath + "/" + file.getName())
                        .collect(Collectors.toList());
                model.addAttribute("images", imageUrls);
            }
        }
        return "gallery";
    }
}
