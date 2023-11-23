package com.synchrony.usermanagement.repository;

import com.synchrony.usermanagement.models.UserImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImagesRepository extends JpaRepository<UserImages,String> {

    void deleteByDeleteHash(String deleteHash);

}
