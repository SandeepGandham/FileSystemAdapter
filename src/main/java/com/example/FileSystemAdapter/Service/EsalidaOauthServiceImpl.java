package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Models.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EsalidaOauthServiceImpl implements EsalidaOauthService {

    private RestTemplate restTemplate;

    @Value("${esalida-oauth-base-url}")
    String esalidaOauthBaseUrl;

    private  String tokenEndpoint= "/oauth/token";

    private  String grantType="password";

    public EsalidaOauthServiceImpl(){

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);

    }


    @Override
    public String login(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic b2F1dGgtY2xpZW50OnNlY3JldA==");
        HttpEntity requestEntity = new HttpEntity(headers);
        String loginUrl = esalidaOauthBaseUrl+tokenEndpoint+"?username="+username+"&password="+password+"&grant_type="+grantType;

       ResponseEntity<AccessToken> result = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, AccessToken.class);

        //ResponseEntity<AccessToken> result = restTemplate.exchange("localhost:4001/esalida/oauth/oauth/token?username=santhosh&password=password&grant_type=password", HttpMethod.POST, requestEntity, AccessToken.class);

        //ResponseEntity<AccessToken> result =restTemplate.postForEntity("http://localhost:4001/esalida/oauth/oauth/token?username=santhosh&password=password&grant_type=password", requestEntity, AccessToken.class);

        return result.getBody().getAccess_token();
    }
}
