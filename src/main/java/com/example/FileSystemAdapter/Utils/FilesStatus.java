package com.example.FileSystemAdapter.Utils;

import java.io.*;

public class FilesStatus {

    private String FilesStatusFileName = "FilesStatus.txt";

    public void setFilesStatus(String status) throws IOException {
        FileWriter fileWriter = new FileWriter(FilesStatusFileName, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(status);
        bufferedWriter.close();
    }

    public String getFilesStatus() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(FilesStatusFileName));
        String status = in.readLine();
        in.close();
        return status;
    }
}
