package com.synchrony.usermanagement.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_images")
public class UserImages implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(name="image_id",nullable = false)
    private String imageId;

    @Column(name="image_link",nullable = false)
    private String imageLink;

    @Column(name="delete_hash",nullable = false)
    private String deleteHash;

    @Column(name="login",nullable = false)
    private String login;
}
