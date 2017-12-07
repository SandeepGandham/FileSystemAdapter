package com.example.FileSystemAdapter.Service;

import java.util.concurrent.CompletableFuture;

public interface FileWalkerService {

    public CompletableFuture<String> callFileWalker(String drivePath) throws InterruptedException;
}
