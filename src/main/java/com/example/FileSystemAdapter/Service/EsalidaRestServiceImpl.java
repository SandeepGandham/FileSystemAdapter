package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Utils.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class EsalidaRestServiceImpl implements EsalidaRestService{

    private RestTemplate restTemplate;

    @Value("${esalida-rest-base-url}")
    String esalidaRestBaseUrl;



    private String accessToken;

    private String getListOfUploadedFilesEndpoint="/filesadapter/file/";

    private String startIndexingEndpoint="/filesadapter/file/index/";

    private EsalidaOauthService esalidaOauthService;

    @Autowired
    public EsalidaRestServiceImpl(EsalidaOauthService esalidaOauthService){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);
        this.esalidaOauthService=esalidaOauthService;
    }

    @Override
    public List<String> getListOfUploadedFiles(long userId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String getListOfUploadedFilesUrl = esalidaRestBaseUrl+getListOfUploadedFilesEndpoint+userId;
        UserStore userStore = new UserStore();
        accessToken = userStore.getAccessToken();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity requestEntity = new HttpEntity(headers);
        try {
            ResponseEntity<List<String>> result = restTemplate.exchange(getListOfUploadedFilesUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<String>>() {
            });
            return result.getBody();
        }
        catch (HttpClientErrorException e){
            //System.out.println(e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                esalidaOauthService.getAccessTokenByRefreshToken();
                accessToken = userStore.getAccessToken();
                HttpHeaders newHeaders = new HttpHeaders();
                newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer "+ accessToken);
                HttpEntity newRequestEntity = new HttpEntity(newHeaders);
                ResponseEntity<List<String>> response = restTemplate.exchange(getListOfUploadedFilesUrl, HttpMethod.GET, newRequestEntity, new ParameterizedTypeReference<List<String>>(){});
                return response.getBody();
            }
        }
//
        //System.out.println(result.getBody());
        return null;
    }

    @Override
    public void startIndexing(long userId) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        String startIndexingUrl = esalidaRestBaseUrl + startIndexingEndpoint + userId;
        UserStore userStore = new UserStore();
        accessToken = userStore.getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity requestEntity = new HttpEntity(headers);
        try {
            ResponseEntity<Void> result = restTemplate.exchange(startIndexingUrl, HttpMethod.GET, requestEntity, Void.class);
        } catch (HttpClientErrorException e) {
            //System.out.println(e.getStatusCode());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED){
                esalidaOauthService.getAccessTokenByRefreshToken();
                accessToken = userStore.getAccessToken();
                HttpHeaders newHeaders = new HttpHeaders();
                newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer "+ accessToken);
                HttpEntity newRequestEntity = new HttpEntity(newHeaders);
                ResponseEntity<Void> response = restTemplate.exchange(startIndexingUrl, HttpMethod.GET, newRequestEntity, Void.class);
            }
        }
    }


}
