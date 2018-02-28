package com.example.FileSystemAdapter.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

public interface DrivesService {
    public HashMap<Integer, HashMap> showDrives()  throws JsonProcessingException;

}
