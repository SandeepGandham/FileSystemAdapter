package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Models.SelectedDrives;
import com.example.FileSystemAdapter.Service.FileUploadService;
import com.example.FileSystemAdapter.Service.FileWalkerService;
import com.example.FileSystemAdapter.Utils.FilesStatus;
import com.example.FileSystemAdapter.Utils.FilesUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class FileWalkerController {

    private static final Logger logger = LoggerFactory.getLogger(FileWalkerController.class);

    private final FileWalkerService fileWalkerService;

    private final FileUploadService fileUploadService;

    private final FilesUploadUtil filesUploadUtil;

    private String inProgress = "In progress";

    FileWalkerController(FileWalkerService fileWalkerService, FileUploadService fileUploadService, FilesUploadUtil filesUploadUtil){
        this.fileWalkerService = fileWalkerService;
        this.fileUploadService = fileUploadService;
        this.filesUploadUtil=filesUploadUtil;
    }

    @RequestMapping(value = "/filewalker/{userId}", method = RequestMethod.POST)
    public String fileWalker(@RequestBody SelectedDrives selectedDrives, @PathVariable long userId) throws Exception{
        FileWriter fileWriter = new FileWriter("UserId.txt", false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(userId));
        bufferedWriter.close();
        List<String> drives =  selectedDrives.getKey();
        List<CompletableFuture<String>> totalProcessingStatus = new ArrayList<CompletableFuture<String>>();
        System.out.println(userId);
        FilesStatus filesStatus = new FilesStatus();
        filesStatus.setFilesStatus(inProgress);
        for( String drive : drives){
            CompletableFuture<String> driveProcessingStatus = fileWalkerService.callFileWalker(drive);
            totalProcessingStatus.add(driveProcessingStatus);
        }
        fileUploadService.startFilesUpload(totalProcessingStatus);
        return "Finished";
    }

    @RequestMapping(value = "/upload/status", method = RequestMethod.GET)
    public String fileUploadStatus() throws IOException {
        return filesUploadUtil.getUpdateStatus();
    }

    @RequestMapping(value = "/files/status", method = RequestMethod.GET)
    public String jsonFilesStatus() throws IOException {
        FilesStatus filesStatus = new FilesStatus();
        return filesStatus.getFilesStatus();
    }
}
