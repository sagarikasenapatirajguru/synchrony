package com.synchrony.usermanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = true)
    private String imageLink;
    @Column(nullable = true)
    private String profession;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "login", referencedColumnName = "login")
    List<UserImages> userImages = new ArrayList< >();

}
