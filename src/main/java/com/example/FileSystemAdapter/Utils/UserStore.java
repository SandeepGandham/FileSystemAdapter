package com.example.FileSystemAdapter.Utils;

import java.io.*;

public class UserStore {

    private String filesStatusFileName = "FileStatus.txt";

    private String userIdFileName="UserId.txt";

    private String accessTokenFileName="AccessToken.txt";


    public void setFilesStatus(String status) throws IOException {
        FileWriter fileWriter = new FileWriter(filesStatusFileName, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(status);
        bufferedWriter.close();
    }

    public String getFilesStatus() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filesStatusFileName));
        String status = in.readLine();
        in.close();
        return status;
    }

    public void setUserId(String userId) throws IOException {
        FileWriter fileWriter = new FileWriter("UserId.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(userId));
        bufferedWriter.close();
    }

    public long getUserId() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(userIdFileName));
        String userId = br.readLine();
        br.close();
        return Long.parseLong(userId);
    }

    public void setAccessToken(String accessToken) throws IOException {
        FileWriter fileWriter = new FileWriter(accessTokenFileName, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(accessToken);
        bufferedWriter.close();
    }

    public String getAccessToken() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(accessTokenFileName));
        String accessToken = in.readLine();
        in.close();
        return accessToken;
    }
}
