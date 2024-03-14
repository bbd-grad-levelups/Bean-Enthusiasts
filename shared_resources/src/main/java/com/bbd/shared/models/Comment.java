package com.bbd.shared.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int comment_id;
    int post_id;
    int user_id;
    String comment_info;
    Timestamp date_posted;

    public Comment(int post_id, int user_id, String comment_info,Timestamp date_posted) {
        this.post_id = post_id;
        this.user_id = user_id;
        this.comment_info = comment_info;
        this.date_posted = date_posted;
    }
}
