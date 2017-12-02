package com.example.FileSystemAdapter.Models;



public class FileMetaData {

    String fileName;
    String contentType;
    String createdTime;
    String lastAccessTime;
    String lastModifiedTime;
    String folderName;
    long size;
    String parent;



    public String getFileName(){
        return  fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public String getLastAccessTime() {
        return lastAccessTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public String getFolderName() {
        return folderName;
    }

    public long getSize() {
        return size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setLastAccessTime(String lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getParent() {return parent;    }

    public void setParent(String parent) {this.parent = parent;}

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
