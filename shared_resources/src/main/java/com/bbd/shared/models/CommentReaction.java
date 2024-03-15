package com.bbd.shared.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int comment_reaction_id;
    int comment_id;
    int reaction_id;

    public CommentReaction(int comment_id, int reaction_id, Timestamp datePosted) {
        this.comment_id = comment_id;
        this.reaction_id = reaction_id;
    }

    public CommentReaction(int commentID, int reactionID) {
       this.comment_id = commentID;
       this.reaction_id = reactionID;
    }
}
