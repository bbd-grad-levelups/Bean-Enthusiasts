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
public class ReactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reaction_type_id;
    String bean_name;

    public ReactionType(int reactionId) {
        this.reaction_type_id = reactionId;

    }
}
