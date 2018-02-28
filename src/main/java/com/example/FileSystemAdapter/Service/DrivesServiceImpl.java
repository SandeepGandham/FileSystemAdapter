package com.example.FileSystemAdapter.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Service
public class DrivesServiceImpl implements DrivesService {

    @Override
    public HashMap<Integer, HashMap> showDrives() throws JsonProcessingException {
        HashMap<Integer, HashMap> driveDetails = new HashMap<>();
        try {
            FileWriter fileWriter = new FileWriter("Drives.txt", false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            File[] drives = File.listRoots();
            FileSystemView fsv = FileSystemView.getFileSystemView();
            for (int i = 0; i < drives.length; i++) {
                HashMap<String, Object> mapDriveDetails = new HashMap<>();
                mapDriveDetails.put("type", fsv.getSystemTypeDescription(drives[i]));
                mapDriveDetails.put("driveName", drives[i]);
                mapDriveDetails.put("totalSpace", drives[i].getTotalSpace());
                mapDriveDetails.put("freeSpace", drives[i].getFreeSpace());
                driveDetails.put(i, mapDriveDetails);
            }

            Iterator<Map.Entry<Integer, HashMap>> iterator = driveDetails.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, HashMap> pairs = iterator.next();
                bufferedWriter.write(pairs.getValue() + "\n");
            }
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (driveDetails);
    }
}
