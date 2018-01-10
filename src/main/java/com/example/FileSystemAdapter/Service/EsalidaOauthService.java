package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Models.Employee;

import java.io.IOException;
import java.util.List;

public interface EsalidaOauthService {

    public List<Employee> login(String username, String password) throws IOException;

    public List<Employee> getAllEmployees() throws IOException;

    public void getAccessTokenByRefreshToken();

}
