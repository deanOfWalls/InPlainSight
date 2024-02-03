package com.deanOfWalls.InPlainSight.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@Service
public class SteganographyService {
    public void createRar(List<File> files, String password) {
    }

    public void catFiles(File decoyImage, File rarFile){
    }

    public void extractFiles(File stegoImage, String password){
    }

    public File downloadFiles(List<File> filesToDownload){
        return null;
    }


}
