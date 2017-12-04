package com.example.FileSystemAdapter.Service;

import com.example.FileSystemAdapter.Utils.FileWalkerUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FileWalkerService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileWalkerService.class);

    private RestTemplate restTemplate;

    private String fileUpoadEndPoint = "/filesadapter/upload";

    private String accessToken;


    public FileWalkerService(){
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setBufferRequestBody(false);
        this.restTemplate = new RestTemplate(requestFactory);
    }

    @Value("${esalida-rest-base-url}")
    String esalidaRestBaseUrl;

    @Value("${access-token-file-name}")
    String accessTokenFileName;

    @Async
    public CompletableFuture<String> callFileWalker(String drivePath) throws InterruptedException {

        Path path = Paths.get(drivePath);
        FileWalkerUtil fileWalkerUtil = new FileWalkerUtil();
        fileWalkerUtil.setDrivePath(path);
        try {
            Files.walkFileTree(path, fileWalkerUtil);
            //File folder = new File(path.toString().substring(0, path.toString().length() - 2)+"drive");
            File folder = new File("jsons");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
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
                        headers.add("Authorization", "Bearer "+accessToken);
                        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

                        ResponseEntity<String> result = restTemplate.exchange(esalidaRestBaseUrl+fileUpoadEndPoint, HttpMethod.POST, requestEntity, String.class);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    File file = new File(listOfFiles[i].getName());
                    file.delete();

                }
            }


        } catch (IOException ex) {
            Logger.getLogger(FileWalkerUtil.class.getName()).log(Level.SEVERE, null, ex);

        }
        return CompletableFuture.completedFuture("Finished");
    }

}
