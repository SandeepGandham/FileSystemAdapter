package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Utils.FileWalkerUtil;
import com.example.FileSystemAdapter.Utils.UserStore;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileWalkerServiceImpl implements FileWalkerService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileWalkerServiceImpl.class);

    private long userId;

    @Value("${esalida-rest-base-url}")
    String esalidaRestBaseUrl;

    @Value("${access-token-file-name}")
    String accessTokenFileName;

    @Autowired
    FileWalkerUtil fileWalkerUtil;


    @Async
    public CompletableFuture<String> callFileWalker(String drivePath) throws InterruptedException {

        Path path = Paths.get(drivePath);
        //FileWalkerUtil fileWalkerUtil = new FileWalkerUtil();
        fileWalkerUtil.setDrivePath(path);
        try {
            UserStore userStore = new UserStore();
            //userStore.setFilesStatus(inProgress);
            logger.info("Creating Json Files");
            Files.walkFileTree(path, fileWalkerUtil);
            logger.info("Json files are successfully created");

        } catch (IOException ex) {
            Logger.getLogger(FileWalkerUtil.class.getName()).log(Level.SEVERE, null, ex);

        }
        return CompletableFuture.completedFuture("Finished");
    }

}
