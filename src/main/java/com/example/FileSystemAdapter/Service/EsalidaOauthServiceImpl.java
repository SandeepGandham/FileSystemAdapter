package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Models.AccessToken;
import com.example.FileSystemAdapter.Models.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

@Service
public class EsalidaOauthServiceImpl implements EsalidaOauthService {

    private RestTemplate restTemplate;

    @Value("${esalida-oauth-base-url}")
    String esalidaOauthBaseUrl;

    @Value("${access-token-file-name}")
    String accessTokenFileName;

    private  String tokenEndpoint= "/oauth/token";

    private String accessToken;


    private String getAllEmployeesEndpoint="/api/admin/employee/all";

    private  String grantType="password";

    public EsalidaOauthServiceImpl(){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);

    }


    @Override
    public List<Employee> login(String username, String password) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic b2F1dGgtY2xpZW50OnNlY3JldA==");
        HttpEntity requestEntity = new HttpEntity(headers);
        String loginUrl = esalidaOauthBaseUrl + tokenEndpoint + "?username=" + username + "&password=" + password + "&grant_type=" + grantType;
        ResponseEntity<AccessToken> result = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, AccessToken.class);
        FileWriter fileWriter = new FileWriter("AccessToken.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(result.getBody().getAccess_token());
        bufferedWriter.close();
        List<Employee> employeeList = getAllEmployees();
        return employeeList;
    }

    @Override
    public List<Employee> getAllEmployees() throws IOException {

        HttpHeaders headers = new HttpHeaders();
        String getAllEmployeesUrl = esalidaOauthBaseUrl+getAllEmployeesEndpoint;
        BufferedReader in = new BufferedReader(new FileReader(accessTokenFileName));
        accessToken = in.readLine();
        in.close();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<List<Employee>> result = restTemplate.exchange(getAllEmployeesUrl, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<List<Employee>>(){});
        return result.getBody();

    }



}
