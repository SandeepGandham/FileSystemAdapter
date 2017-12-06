package com.example.FileSystemAdapter.Controller;

import com.example.FileSystemAdapter.Models.SelectedDrives;
import com.example.FileSystemAdapter.Service.FileWalkerService;
import com.example.FileSystemAdapter.Utils.FilesStatus;
import com.example.FileSystemAdapter.Utils.UploadStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class FileWalkerController {

    private static final Logger logger = LoggerFactory.getLogger(FileWalkerController.class);

    private final FileWalkerService fileWalkerService;

    FileWalkerController(FileWalkerService fileWalkerService){
        this.fileWalkerService=fileWalkerService;
    }

    @RequestMapping(value = "/filewalker/{userId}", method = RequestMethod.POST)
    public String fileWalker(@RequestBody SelectedDrives selectedDrives, @PathVariable long userId) throws Exception{

//        long start = System.currentTimeMillis();
//
//        logger.info("Calling Async 1st time");
//        CompletableFuture<String> folder1 = fileWalkerService.callFileWalker("/home/vivek/Sandeep/printName/src/main/java/com/example");
//        logger.info("Calling Async 2nd time");
//        CompletableFuture<String> folder2 = fileWalkerService.callFileWalker("/home/vivek/Sandeep/Java");
//        CompletableFuture.allOf(folder1,folder2);

        //System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
//        System.out.println("--> " + folder1.get());
//        System.out.println("--> " + folder2.get());
        fileWalkerService.setUserId(userId);
        List<String> drives =  selectedDrives.getKey();
        //CompletableFuture<List<String>> folder = new CompletableFuture<List<String>>();
        System.out.println(userId);
        for( String drive : drives){
            //fileWalkerService.callFileWalker("/home/vivek/Sandeep");
            fileWalkerService.callFileWalker(drive);
        }
        return "Finished";
    }

    @RequestMapping(value = "/upload/status", method = RequestMethod.GET)
    public String fileUploadStatus() throws IOException {
        UploadStatus uploadStatus = new UploadStatus();
        return fileWalkerService.getUpdateStatus();
        //return uploadStatus.getUploadStatus();
    }

    @RequestMapping(value = "/files/status", method = RequestMethod.GET)
    public String jsonFilesStatus() throws IOException {
        FilesStatus filesStatus = new FilesStatus();
        return filesStatus.getFilesStatus();
    }
}
