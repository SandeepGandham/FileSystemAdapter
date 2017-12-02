package com.example.FileSystemAdapter.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam ("username") String userId, @RequestParam("password") String password){
        return null;
    }

}
