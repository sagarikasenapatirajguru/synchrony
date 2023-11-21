package com.synchrony.usermanagement.repository;

import com.synchrony.usermanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,String> {
    User findUserByLogin(String login);
    @Modifying
    @Query("update User set imageLink=:link where login=:login")
    void updateimageLinkByLogin(@Param("login") String login,@Param("link") String link);

}
