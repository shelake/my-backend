package com.example.product.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userID;

    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Likes> likes;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comments> comments;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Groups> groups;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Posts> posts;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notifications> notifications;

    @OneToMany(mappedBy = "userID1", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Friends> userID1;

    @OneToMany(mappedBy = "userID2", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Friends> userID2;

    @OneToMany(mappedBy = "senderID", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> senderID;

    @OneToMany(mappedBy = "receiverID", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> receiverID;
}
