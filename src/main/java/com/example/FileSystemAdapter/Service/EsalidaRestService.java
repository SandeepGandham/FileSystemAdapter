package com.example.FileSystemAdapter.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface EsalidaRestService {

    public List<String> getListOfUploadedFiles(long userId)throws IOException;

    public void startIndexing(long userId) throws IOException;
}
