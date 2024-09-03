package com.example.product.entity;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID")
    private int postID;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    @CreationTimestamp
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "userID")
    @JsonIgnore
    private Users users;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Likes> likes;

    @OneToMany(mappedBy = "posts", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comments> comments;
}