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
public class PostReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int post_reaction_id;
    int post_id;
    int reaction_id;

    public PostReaction(int post_id, int reaction_id) {
        this.post_id = post_id;
        this.reaction_id = reaction_id;
    }

    public PostReaction(int reaction_id){
        this.reaction_id = reaction_id;
    }
    
}
