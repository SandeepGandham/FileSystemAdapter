package com.example.FileSystemAdapter.Models;

import java.util.List;

/**
 * Created by tecnicsdev on 11/12/17.
 */
public class EsalidaFile {


    private String createdDateTime;
    private String extension;
    private String folderName;
    private String id;
    private String lastModifiedBy;
    private String lastModifiedDateTime;
    private String fileName;
    private List<String> ownerName;
    private String parentReference;
    private Long size;
    private String userId;
    private String webUrl;
    private String documentId;
    private String timeSeries;
    private String storageType;
    private String indexDateTime;
    private String content;


    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public List<String> getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(List<String> ownerName) {
        this.ownerName = ownerName;
    }

    public String getParentReference() {
        return parentReference;
    }

    public void setParentReference(String parentReference) {
        this.parentReference = parentReference;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    public String getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(String timeSeries) {
        this.timeSeries = timeSeries;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getIndexDateTime() {
        return indexDateTime;
    }

    public void setIndexDateTime(String indexDateTime) {
        this.indexDateTime = indexDateTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
