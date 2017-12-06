package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Models.CheckLoginPayload;
import com.example.FileSystemAdapter.Models.Employee;
import com.example.FileSystemAdapter.Service.EsalidaOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class CheckLoginController {

    private EsalidaOauthService esalidaOauthService;


    @Autowired
    public CheckLoginController(EsalidaOauthService esalidaOauthService) {
        this.esalidaOauthService = esalidaOauthService;

    }

    @RequestMapping(value = "checklogin", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"}, headers = "content-type=application/x-www-form-urlencoded" )
    public String checkLogin(@ModelAttribute("checkLoginPayload") @Valid CheckLoginPayload checkLoginPayload, BindingResult bindingResult, Model model) throws IOException {

        List<Employee> employeeList = esalidaOauthService.login(checkLoginPayload.getUsername(), checkLoginPayload.getPassword());
        model.addAttribute("employeeList", employeeList);
        return "EmployeesTemplate";
    }




}

