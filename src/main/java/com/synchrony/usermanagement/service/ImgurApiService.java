package com.synchrony.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImgurApiService {
    @Autowired
    private OAuth2RestOperations restTemplate;

    public ImgurApiService(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.getAccessToken();
    }

    public <T> T getUploadedImage(String resourceUrl, Class<T> resultType) {
        return restTemplate.getForObject(resourceUrl, resultType);
    }

    public String postUploadedImage(String resourceUrl, MultipartFile file) {
        return restTemplate.postForObject("",file,String.class);
    }

}
