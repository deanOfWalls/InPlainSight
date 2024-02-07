package com.deanOfWalls.InPlainSight.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SteganographyService {

    public void createRar(List<MultipartFile> files, String rarFileName, String password) throws IOException, InterruptedException {
        // validate input parameters
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files were selected to add to the .rar archive!");
        }

        // Check if the password is not empty and is alphanumeric
        if (!password.isEmpty() && !password.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("Password must be alphanumeric.");
        }

        // Create a temporary directory to store the uploaded files
        File tempDir = Files.createTempDirectory("tempUploadDir").toFile();

        // Save each uploaded file to the temporary directory
        for (MultipartFile file : files) {
            File tempFile = new File(tempDir, file.getOriginalFilename());
            file.transferTo(tempFile);
        }

        // Initialize the command list for ProcessBuilder
        List<String> commandList = new ArrayList<>();
        commandList.add("rar");
        commandList.add("a");

        // add the password option if a password is provided and is not empty
        if (!password.isEmpty()) {
            commandList.add("-p" + password);
        }

        // add the .rar file name as an argument
        commandList.add(rarFileName);

        // add the selected files as the final arguments
        for (File file : tempDir.listFiles()) {
            commandList.add(file.getAbsolutePath());
        }

        // Configure ProcessBuilder with the command list
        ProcessBuilder processBuilder = new ProcessBuilder(commandList);

        // start the process
        Process process = processBuilder.start();

        // wait for process to complete
        int exitCode = process.waitFor();

        // check if the command executed successfully (exit code 0)
        if (exitCode != 0) {
            throw new IOException("Error creating .rar file");
        }
    }
}
