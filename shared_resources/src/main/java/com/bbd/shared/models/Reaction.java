package com.bbd.shared.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reaction_id;
    int user_id;
    int reaction_type_id;
    Timestamp date_of_reaction;

    public Reaction(int user_id, int reaction_type_id, Timestamp date_of_reaction) {
        this.user_id = user_id;
        this.reaction_type_id = reaction_type_id;
        this.date_of_reaction = date_of_reaction;

    }
}
