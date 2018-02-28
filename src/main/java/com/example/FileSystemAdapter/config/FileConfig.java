package com.example.FileSystemAdapter.config;

import com.example.FileSystemAdapter.Models.ResponseObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {

    @Bean
    public ResponseObject responseObject(){
        return new ResponseObject();
    }
}
