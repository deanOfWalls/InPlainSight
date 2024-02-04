package com.deanOfWalls.InPlainSight.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Service
public class SteganographyService {
    public void createRar(List<File> files, String rarFileName, String password) throws IOException, InterruptedException {
        // validate input parameters
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("No files were selected to add to the .rar archive!");
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
        for (File file : files) {
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
    }

    public void catFiles(File decoyImage, File rarFile) {
    }

    public void extractFiles(File stegoImage, String password) {
    }

    public File downloadFiles(List<File> filesToDownload) {
        return null;
    }


}
