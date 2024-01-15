package com.deanOfWalls.InPlainSight.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;

@Service
public class SteganographyService {

    public byte[] encodeFiles(MultipartFile decoyImage, MultipartFile fileToHide, String password) throws IOException, InterruptedException {
        byte[] decoyImageBytes = decoyImage.getBytes();
        byte[] fileToHideBytes = fileToHide.getBytes();

        String tempDir = System.getProperty("java.io.tmpdir");

        // Convert byte arrays to temporary files
        File decoyImageFile = createTempFile(decoyImageBytes, tempDir, "decoyImage.png");
        File fileToHideFile = createTempFile(fileToHideBytes, tempDir, "fileToHide.rar");

        // Build the RAR command
        String[] rarCommand = {
                "rar",
                "a",
                "-p" + password,
                tempDir + File.separator + "hidden_files.rar",
                decoyImageFile.getPath(),
                fileToHideFile.getPath()
        };

        // Execute the RAR command using ProcessBuilder
        ProcessBuilder rarBuilder = new ProcessBuilder(rarCommand);
        rarBuilder.directory(new File(tempDir));
        Process rarProcess = rarBuilder.start();
        rarProcess.waitFor();

        // Concatenate decoy image and .rar to create stego image
        String stegoImagePath = tempDir + File.separator + "stegoImage.png";
        String[] concatCommand = {
                "cat",
                decoyImageFile.getPath(),
                tempDir + File.separator + "hidden_files.rar",
                stegoImagePath
        };

        // Execute the concatenation command using ProcessBuilder
        ProcessBuilder concatBuilder = new ProcessBuilder(concatCommand);
        concatBuilder.directory(new File(tempDir));
        Process concatProcess = concatBuilder.start();
        concatProcess.waitFor();

        // Load the stego image back into a byte array
        return loadFileToByteArray(stegoImagePath);
    }

    public byte[] decodeFiles(byte[] stegoImage, String password) throws IOException, InterruptedException {
        String tempDir = System.getProperty("java.io.tmpdir");

        // Convert byte array to a temporary file
        File stegoImageFile = createTempFile(stegoImage, tempDir, "stegoImage.png");

        // Build the unrar command
        String[] unrarCommand = {
                "unrar",
                "x",
                "-p" + password,
                stegoImageFile.getPath(),
                tempDir
        };

        // Execute the unrar command using ProcessBuilder
        ProcessBuilder unrarBuilder = new ProcessBuilder(unrarCommand);
        unrarBuilder.directory(new File(tempDir));
        Process unrarProcess = unrarBuilder.start();
        unrarProcess.waitFor();

        // Assuming you want to return the extracted data as a byte array
        return loadFileToByteArray(tempDir + File.separator + "extracted_image.png");
    }

    private File createTempFile(byte[] data, String tempDir, String fileName) throws IOException {
        File tempFile = new File(tempDir, fileName);
        tempFile.deleteOnExit();

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(data);
        }

        return tempFile;
    }

    private byte[] loadFileToByteArray(String filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }
}
