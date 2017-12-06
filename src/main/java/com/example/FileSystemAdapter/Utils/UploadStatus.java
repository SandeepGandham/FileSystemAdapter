package com.example.FileSystemAdapter.Utils;

import java.io.*;

public class UploadStatus {

    private String uploadStatusFile = "UploadStatus.txt";

    public void setUploadStatus(String status) throws IOException {
        FileWriter fileWriter = new FileWriter(uploadStatusFile, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(status);
        bufferedWriter.close();
    }

    public String getUploadStatus() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(uploadStatusFile));
        String status = in.readLine();
        in.close();
        return status;
    }
}
