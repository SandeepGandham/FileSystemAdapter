package com.example.FileSystemAdapter.Models;

public class DriveDetails {

    String DriveName;
    String Type;
    long TotalSpace;
    long FreeSpace;

    public String getDriveName() {
        return DriveName;
    }

    public String getType() {
        return Type;
    }

    public long getTotalSpace() {
        return TotalSpace;
    }

    public long getFreeSpace() {
        return FreeSpace;
    }

    public void setDriveName(String driveName) {
        DriveName = driveName;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setTotalSpace(long totalSpace) {
        TotalSpace = totalSpace;
    }

    public void setFreeSpace(long freeSpace) {
        FreeSpace = freeSpace;
    }
}
