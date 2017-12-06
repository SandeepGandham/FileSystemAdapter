package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Service.EsalidaRestService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class EsalidaRestController {

    private EsalidaRestService esalidaRestService;

    public EsalidaRestController(EsalidaRestService esalidaRestService){
        this.esalidaRestService=esalidaRestService;
    }

    @RequestMapping(value = "/start/{userId}", method = RequestMethod.GET)
    public void index(@PathVariable long userId) throws IOException {
        esalidaRestService.startIndexing(userId);
    }
}
