package com.example.FileSystemAdapter.Controller;
import com.example.FileSystemAdapter.Models.ResponseObject;
import com.example.FileSystemAdapter.Service.DrivesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@Controller
public class DisplayListOfDrivesController {
//    private Logger logger = LoggerFactory.getLogger(DisplayListOfDrivesController.class);
    private  final DrivesService drivesService;

    private final ResponseObject responseObject;


    @Autowired
    DisplayListOfDrivesController(DrivesService drivesService, ResponseObject responseObject){

        this.drivesService=drivesService;

        this.responseObject=responseObject;

    }

    @RequestMapping(value = "drives",  method= RequestMethod.GET)

    ResponseEntity<ResponseObject> getDrives() throws JsonProcessingException {

       HashMap<Integer, HashMap> listDrives = drivesService.showDrives();

        if (listDrives == null) {

            responseObject.setStatus("Error");

            responseObject.setDescription("No drives!");

            responseObject.setData(null);

            ResponseEntity<ResponseObject> responseEntity = new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

        responseObject.setStatus("success");

        responseObject.setDescription("Drives found");

        responseObject.setData(listDrives);

        ResponseEntity<ResponseObject> responseEntity = new ResponseEntity<>(responseObject, HttpStatus.OK);

        return responseEntity;

    }


    @RequestMapping(value = "data", method = RequestMethod.GET)
    String RoughTemplate(Model model) throws JsonProcessingException{

       HashMap<Integer, HashMap> listDrives = drivesService.showDrives();

        model.addAttribute("listDrives",listDrives);

        return "DisplayDrivesTemplate";

    }

}
