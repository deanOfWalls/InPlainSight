package com.deanOfWalls.InPlainSight.service;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class SteganographyService {
    public void createRar(List<MultipartFile> files, String rarFileName, String password) throws IOException, InterruptedException {
        // validate input parameters
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files were selected to add to the .rar archive!");
        }

        // Create a temporary directory to store the uploaded files
        File tempDir = Files.createTempDirectory("tempUploadDir").toFile();

        try {
            // Save each uploaded file to the temporary directory
            for (MultipartFile file : files) {
                File tempFile = new File(tempDir, file.getOriginalFilename());
                file.transferTo(tempFile);
            }

            // build command for creating .rar file
            ProcessBuilder processBuilder = new ProcessBuilder();
            // the 'a' command option adds files to an archive
            processBuilder.command("rar", "a");

            // add the password option if a password is provided
            if (!password.isEmpty()) {
                // the '-p' + "YourPassword" command adds a password to the archive
                processBuilder.command("-p" + password);
            }

            // add the .rar file name as an argument
            processBuilder.command(rarFileName);

            // add the selected files as the final arguments
            for (File file : tempDir.listFiles()) {
                processBuilder.command(file.getAbsolutePath());
            }

            // start the process
            Process process = processBuilder.start();

            // wait for process to complete
            int exitCode = process.waitFor();

            // check if the command executed successfully (exit code 0)
            if (exitCode != 0) {
                throw new IOException("Error creating .rar file");
            }
        } finally {
            // Clean up: Delete the temporary directory and its contents
            FileUtils.deleteDirectory(tempDir);
        }
    }
}
