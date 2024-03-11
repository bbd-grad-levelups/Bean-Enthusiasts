package com.bbd.shared.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int comment_reaction_id;
    int comment_id;
    int reaction_id;

    public CommentReactions(int comment_id, int reaction_id) {
        this.comment_id = comment_id;
        this.reaction_id = reaction_id;
    }
}
