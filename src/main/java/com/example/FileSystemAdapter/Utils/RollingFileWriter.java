package com.example.FileSystemAdapter.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class RollingFileWriter {

    private long maxFileSize;

    private String filePattern;

    private String fileExtension;

    private File fileName;

    private int i = 1;

    private int counter = 0;

    public RollingFileWriter(String filePattern, String fileExtension, long maxFileSize){
        this.maxFileSize=maxFileSize;
        this.fileExtension=fileExtension;
        this.filePattern=filePattern;
    }

    private void createFile() throws IOException {
        fileName = new File(filePattern + i + fileExtension);
        fileName.getParentFile().mkdirs();
        if (!fileName.exists()) {
            fileName.createNewFile();
        }
        this.setJsonFile(fileName);
    }

    private void createNewFile() throws IOException {
        fileName = new File(filePattern + i++ + fileExtension);
        if (!fileName.exists()) {
            fileName.createNewFile();
        }
        this.setJsonFile(fileName);
    }

    private void setJsonFile(File jsonFile){
        jsonFile=jsonFile;
    }

    public void write(String jsonData) throws IOException {

        if (counter == 0){
            this.createFile();
            counter++;
        }
        if(fileName.length()>maxFileSize) {
            this.createNewFile();
        }
        Files.write(fileName.toPath(), Arrays.asList(jsonData), StandardOpenOption.APPEND);
    }
}
