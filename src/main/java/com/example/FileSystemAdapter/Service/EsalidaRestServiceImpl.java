package com.example.FileSystemAdapter.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class EsalidaRestServiceImpl implements EsalidaRestService{

    private RestTemplate restTemplate;

    @Value("${esalida-rest-base-url}")
    String esalidaRestBaseUrl;

    @Value("${access-token-file-name}")
    String accessTokenFileName;

    private String accessToken;

    private String getListOfUploadedFilesEndpoint="/filesadapter/file/";

    private String startIndexingEndpoint="/filesadapter/file/index/";

    public EsalidaRestServiceImpl(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    @Override
    public List<String> getListOfUploadedFiles(long userId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String getListOfUploadedFilesUrl = esalidaRestBaseUrl+getListOfUploadedFilesEndpoint+userId;
        BufferedReader in = new BufferedReader(new FileReader(accessTokenFileName));
        accessToken = in.readLine();
        in.close();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<List<String>> result = restTemplate.exchange(getListOfUploadedFilesUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<String>>(){});
//        System.out.println(result.getBody());
        return result.getBody();
    }

    @Override
    public void startIndexing(long userId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String startIndexingUrl = esalidaRestBaseUrl + startIndexingEndpoint + userId;
        BufferedReader in = new BufferedReader(new FileReader(accessTokenFileName));
        accessToken = in.readLine();
        in.close();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Void> result = restTemplate.exchange(startIndexingUrl, HttpMethod.GET, requestEntity, Void.class);
    }

}
