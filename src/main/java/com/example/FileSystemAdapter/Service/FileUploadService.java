package com.example.FileSystemAdapter.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FileUploadService {

    public void startFilesUpload(List<CompletableFuture<String>> totalProcessingStatus);
}
