package com.example.FileSystemAdapter.Utils;

import com.example.FileSystemAdapter.Models.FileMetaData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileWalkerUtil implements FileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(FileWalkerUtil.class);

    private RollingFileWriter rollingFileWriter;

    private long userId;

    DateFormat df=new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
    String createdDateTime, lastAccessTime, lastModifiedTime;
    String fileName;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.format("preVisitDirectory: %s\n", dir);
        logger.info(dir.toString());
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        //System.out.format("visitFile: %s\n", file);
//        System.out.println(java.nio.file.Files.getOwner(file));
        if(file.getFileName().toString().indexOf(".") > 0){
            fileName = file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf("."));
        }
        FileMetaData fileMetaData=new FileMetaData();
        fileMetaData.setDocumentId(UUID.randomUUID().toString());
        fileMetaData.setFileName(fileName);
        fileMetaData.setParent(file.getParent().toString());
        fileMetaData.setContentType(file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".") + 1));
        date.setTime(attrs.creationTime().toMillis());
        createdDateTime = dateFormat.format(date);
        fileMetaData.setCreatedTime(createdDateTime);
        date.setTime(attrs.lastAccessTime().toMillis());
        lastAccessTime = dateFormat.format(date);
        fileMetaData.setLastAccessTime(lastAccessTime);
        date.setTime(attrs.lastModifiedTime().toMillis());
        lastModifiedTime = dateFormat.format(date);
        fileMetaData.setLastModifiedTime(lastModifiedTime);
        //fileMetaData.setFolderName(file.getParent().toString().substring(file.getParent().toString().lastIndexOf("\\") + 1));
        fileMetaData.setFolderName(file.getParent().toString().substring(file.getParent().toString().lastIndexOf("/") + 1));
        fileMetaData.setSize(attrs.size());
        fileMetaData.setUserId(userId);
        fileMetaData.setPath(file.toString());
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonInString = objectMapper.writeValueAsString(fileMetaData);
        logger.info(jsonInString);
        rollingFileWriter.write(jsonInString);
        //Files.write(jsonFile.toPath(), Arrays.asList(jsonInString), StandardOpenOption.APPEND);
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        //System.out.format("visitFileFailed: %s\n", file);
        return FileVisitResult.CONTINUE;
    }
    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.format("postVisitDirectory: %s\n", dir);
        //return super.postVisitDirectory(dir, exc);
        return FileVisitResult.CONTINUE;
    }

    public void setDrivePath(Path path){
        //rollingFileWriter = new RollingFileWriter(path.toString().substring(0, path.toString().length() - 2)+"drive/FilesMetaData", ".json",10485760);
        rollingFileWriter = new RollingFileWriter("jsons/FilesMetaData", ".json",10000000);

    }

    public void setUserId(long userIdfromService) {
        System.out.println(userIdfromService);
        userId = userIdfromService;
    }
}
