
package com.synchrony.usermanagement.service;

import com.synchrony.usermanagement.models.ImgurDto;
import com.synchrony.usermanagement.models.ImgurResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class ImgurApiService {

    private HttpClient httpClient;

    public final static String CLIENT_ID = "647c22f645bd76b";

    private static final String IMGUR_URI = "https://api.imgur.com/3/";

    public  HttpResponse<String> uploadToImgur(byte[] data) throws IOException, InterruptedException
    {
        String uuid = java.util.UUID.randomUUID().toString();
        HttpRequest.BodyPublisher body = oneFileBody(data, uuid);
        URI uri = URI.create("https://api.imgur.com/3/upload/");

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "multipart/form-data; boundary=" + uuid)
                .header("Authorization", "Client-ID " + CLIENT_ID)
                .POST(body)
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }



    public static HttpRequest.BodyPublisher oneFileBody(byte[] data, String uuid) throws IOException
    {
        String base64_image = Base64.getEncoder().encodeToString(data);
        String content = String.format("--%1$s\nContent-Disposition: form-data; name=\"type\"\nContent-Type: text/plain\n\nbase64\n--%1$s\nContent-Disposition: form-data; name=\"image\"\n\n%2$s\n--%1$s", uuid, base64_image);
        return HttpRequest.BodyPublishers.ofString(content);
    }

    public  HttpResponse<String> getUploadedImage(String id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.imgur.com/3/image/"+id))
                .header("Authorization", "Client-ID " + CLIENT_ID)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;

    }

    public  HttpResponse<String> deleteUploadedImage(String id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.imgur.com/3/image/"+id))
                .header("Authorization", "Client-ID " + CLIENT_ID)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}

