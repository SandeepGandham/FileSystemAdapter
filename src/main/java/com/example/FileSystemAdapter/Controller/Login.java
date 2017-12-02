package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Service.EsalidaOauthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    private EsalidaOauthService esalidaOauthService;

    public Login(EsalidaOauthService esalidaOauthService){
        this.esalidaOauthService=esalidaOauthService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam ("username") String username, @RequestParam("password") String password){

        String accessToken = esalidaOauthService.login(username, password);

        return accessToken;
    }

}
