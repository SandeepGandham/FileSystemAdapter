package com.example.FileSystemAdapter.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EsalidaOauthServiceImpl implements EsalidaOauthService {

    @Value("${esalida-oauth-base-url}")
    String esalidaOauthBaseUrl;

    @Override
    public String login(String username, String password) {



        return null;
    }
}
