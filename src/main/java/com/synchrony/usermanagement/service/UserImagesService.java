package com.synchrony.usermanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.synchrony.usermanagement.models.ImgurResponse;
import com.synchrony.usermanagement.models.ImgureDeleteResponse;
import com.synchrony.usermanagement.models.UserImages;
import com.synchrony.usermanagement.models.UserImagesDto;
import com.synchrony.usermanagement.repository.UserImagesRepository;
import com.synchrony.usermanagement.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class UserImagesService {

    @Autowired
    UserImagesRepository userImagesRepository;

    @Autowired
    ImgurApiService imgurApiService;


    public List<UserImages> findAll() {
        return userImagesRepository.findAll(Sort.by("id"));
    }

    public ImgurResponse get(final String id) throws IOException, InterruptedException , NotFoundException{
        //Get call to Imgur API to fetch the image info
        HttpResponse<String> response = imgurApiService.getUploadedImage(id);
        if(response.statusCode() == HttpStatus.NOT_FOUND.value()) {
            throw new NotFoundException("The resource does not exist");
        }
        return new ObjectMapper().readValue(response.body(), ImgurResponse.class);
    }

    public Long create(final UserImagesDto userImagesDTO) {
        final UserImages userImages = new UserImages();
        mapToEntity(userImagesDTO, userImages);
        return userImagesRepository.save(userImages).getId();
    }

    public void delete(final String id) throws IOException, InterruptedException {
        //Delete API call
        HttpResponse<String> deleteResponse = imgurApiService.deleteUploadedImage(id);
        ImgureDeleteResponse respObj = new ObjectMapper().readValue(deleteResponse.body(), ImgureDeleteResponse.class);
        //Delete from database
        if(respObj != null && respObj.status == HttpStatus.OK.value()) {
            userImagesRepository.deleteByDeleteHash(id);
        }
    }

    private UserImages mapToEntity(final UserImagesDto userImagesDTO, final UserImages userImages) {
        userImages.setId(userImagesDTO.getId());
        userImages.setLogin(userImagesDTO.getLogin());
        userImages.setDeleteHash(userImagesDTO.getDeleteHash());
        userImages.setLogin(userImagesDTO.getLogin());
        return userImages;
    }
}
