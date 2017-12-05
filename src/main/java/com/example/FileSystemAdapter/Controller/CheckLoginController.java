package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Service.EsalidaOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Login {

    private EsalidaOauthService esalidaOauthService;

    @Autowired
    public Login(EsalidaOauthService esalidaOauthService) {
        this.esalidaOauthService = esalidaOauthService;
    }

    @RequestMapping(value = "checklogin", method = Requ
            estMethod.POST)
    public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password) {

        String loginStatus = esalidaOauthService.login(username, password);

        return loginStatus;
    }




}

