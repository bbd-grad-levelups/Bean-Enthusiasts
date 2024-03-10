package com.bbd.BeanServer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "tag_id", nullable = false)
    private int tagId;

    @Column(name = "post_title", length = 50, nullable = false)
    private String postTitle;

    @Column(name = "post_content", length = 500, nullable = false)
    private String postContent;

    @Column(name = "date_posted")
    private Timestamp datePosted;

    public Post(int userId, int tagId, String postTitle, String postContent, Timestamp datePosted) {
        this.userId = userId;
        this.tagId = tagId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.datePosted = datePosted;
    }
}
