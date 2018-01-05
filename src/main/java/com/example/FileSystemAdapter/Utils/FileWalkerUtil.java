package com.example.FileSystemAdapter.Utils;

import com.example.FileSystemAdapter.Models.EsalidaFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class FileWalkerUtil implements FileVisitor<Path> {

    private static final Logger logger = LoggerFactory.getLogger(FileWalkerUtil.class);

    private RollingFileWriter rollingFileWriter;

    private String storageType = "localFile";

    private long userId;

    @Value("#{'${valid.extensions.list}'.split(',')}")
    private String[] validExtensions;

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
//        System.out.println("owner - " + java.nio.file.Files.getOwner(file));
        if (file.getFileName().toString().indexOf(".") > 0) {
            fileName = file.getFileName().toString().substring(0, file.getFileName().toString().lastIndexOf("."));
        }
        EsalidaFile esalidaFile = new EsalidaFile();
        esalidaFile.setDocumentId(UUID.randomUUID().toString());
        esalidaFile.setFileName(fileName);
        esalidaFile.setParentReference(file.getParent().toString().substring(file.getParent().toString().indexOf('\\') + 1));
        esalidaFile.setExtension(file.getFileName().toString().substring(file.getFileName().toString().lastIndexOf(".") + 1));
        for (String validExtension:validExtensions ) {
            if (validExtension.equals(esalidaFile.getExtension())) {
                Tika tika = new Tika();
                try {
                    esalidaFile.setContent(tika.parseToString(file));
                } catch (TikaException e) {
                    e.printStackTrace();
                }
            }
        }
        date.setTime(attrs.creationTime().toMillis());
        createdDateTime = dateFormat.format(date);
        esalidaFile.setCreatedDateTime(createdDateTime);
        date.setTime(attrs.lastModifiedTime().toMillis());
        lastModifiedTime = dateFormat.format(date);
        esalidaFile.setLastModifiedDateTime(lastModifiedTime);
        esalidaFile.setFolderName(file.getParent().toString().substring(file.getParent().toString().lastIndexOf("\\") + 1));
        //esalidaFile.setFolderName(file.getParent().toString().substring(file.getParent().toString().lastIndexOf("/") + 1));
        esalidaFile.setSize(attrs.size());
        UserStore userStore = new UserStore();
        userId = userStore.getUserId();
        esalidaFile.setUserId(Long.toString(userId));
        esalidaFile.setTimeSeries(lastModifiedTime);
        esalidaFile.setStorageType(storageType);
        esalidaFile.setIndexDateTime(dateFormat.format(Calendar.getInstance().getTime()));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonInString = objectMapper.writeValueAsString(esalidaFile);
        logger.info(jsonInString);
        rollingFileWriter.write(jsonInString);
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

    public void setDrivePath(Path path) {
        rollingFileWriter = new RollingFileWriter("jsons/FilesMetaData", ".json", 10000000);

    }

}