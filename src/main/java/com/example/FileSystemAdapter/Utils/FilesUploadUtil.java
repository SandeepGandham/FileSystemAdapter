package com.example.FileSystemAdapter.Utils;

import com.example.FileSystemAdapter.Service.EsalidaRestService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FilesUploadUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FilesUploadUtil.class);

    private RestTemplate restTemplate;

    private String fileUpoadEndPoint = "/filesadapter/upload/";

    private String accessToken;

    private EsalidaRestService esalidaRestService;

    private long userId;

    private String done = "Done";

    private List<String> totalUploadedFiles = new ArrayList<String>();

    private File[] listOfFiles;

    @Value("${esalida-rest-base-url}")
    String esalidaRestBaseUrl;

    @Value("${access-token-file-name}")
    String accessTokenFileName;


    private String userIdFileName="UserId.txt";

    public FilesUploadUtil(EsalidaRestService esalidaRestService){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);
        this.esalidaRestService=esalidaRestService;
    }


    public void uploadFiles() throws IOException {

        File folder = new File("jsons");
        listOfFiles = folder.listFiles();
        logger.info("Starting upload process");
        BufferedReader br = new BufferedReader(new FileReader(userIdFileName));
        String userIdFromFile = br.readLine();
        br.close();
        userId = Long.parseLong(userIdFromFile);
        List<String> uploadedFilesInServer = esalidaRestService.getListOfUploadedFiles(userId);
        totalUploadedFiles.clear();
        totalUploadedFiles.addAll(uploadedFilesInServer);
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                for (String file:uploadedFilesInServer) {
                    logger.debug(file);
                }
                boolean fileExists = uploadedFilesInServer.contains(listOfFiles[i].getName());
                if (!fileExists) {
                    LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
                    Path filePath = Paths.get(listOfFiles[i].getAbsolutePath());
                    String name = listOfFiles[i].getName();
                    String originalFileName = listOfFiles[i].getName();
                    String contentType = "application/json";
                    byte[] content = null;
                    try {
                        content = Files.readAllBytes(filePath);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                    MultipartFile multipartFile = new MockMultipartFile(name, originalFileName, contentType, content);

                    FileOutputStream fo = new FileOutputStream(listOfFiles[i].getName());
                    fo.write(multipartFile.getBytes());
                    fo.close();
                    map.add("file", new FileSystemResource(originalFileName));
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    try {
                        BufferedReader in = new BufferedReader(new FileReader(accessTokenFileName));
                        accessToken = in.readLine();
                        in.close();
                        headers.add("Authorization", "Bearer " + accessToken);
                        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
                        String uploadUrl = esalidaRestBaseUrl + fileUpoadEndPoint + userId;
                        logger.info("Uploading " + listOfFiles[i].getName() + " to esalida-rest");
                        ResponseEntity<String> result = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity, String.class);
                        logger.info(listOfFiles[i].getName() + " is uploaded successfully");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    totalUploadedFiles.add(listOfFiles[i].getName());
                    File file = new File(listOfFiles[i].getName());
                    file.delete();

                }
            }
        }

        logger.info("Files Uploaded successfully");

    }

    public String getUpdateStatus(){

        int totalFilesCount = listOfFiles.length;
        int uploadedFilesCount = totalUploadedFiles.size();
        if (totalFilesCount == uploadedFilesCount){
            return done;
        }
        return (uploadedFilesCount+" out of "+totalFilesCount+" are uploaded");
    }




}
