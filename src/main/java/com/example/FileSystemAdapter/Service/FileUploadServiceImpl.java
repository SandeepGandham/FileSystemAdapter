package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Utils.FilesStatus;
import com.example.FileSystemAdapter.Utils.FilesUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    private String done = "Done";
    
    private FilesUploadUtil filesUploadUtil;

    @Autowired
    public FileUploadServiceImpl(FilesUploadUtil filesUploadUtil){
        this.filesUploadUtil=filesUploadUtil;
    }

    @Async
    public void startFilesUpload(List<CompletableFuture<String>> totalProcessingStatus){

        CompletableFuture.allOf(totalProcessingStatus.toArray(new CompletableFuture[totalProcessingStatus.size()])).join();
        try {
            for(CompletableFuture<String> result : totalProcessingStatus){
                logger.info(result.get());
            }
            FilesStatus filesStatus = new FilesStatus();
            filesStatus.setFilesStatus(done);
            filesUploadUtil.uploadFiles();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
