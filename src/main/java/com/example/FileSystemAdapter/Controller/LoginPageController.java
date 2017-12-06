package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Models.CheckLoginPayload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {

    @RequestMapping(value = "login")
    String login(Model model){
        model.addAttribute("checkLoginPayload", new CheckLoginPayload());
        return "LoginTemplate";
    }
}
