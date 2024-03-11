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
    int Reactions_id;
    int user_id;
    int Reactions_type_id;
    Timestamp date_of_Reactions;

    public Reaction(int user_id, int Reactions_type_id, Timestamp date_of_Reactions) {
        this.user_id = user_id;
        this.Reactions_type_id = Reactions_type_id;
        this.date_of_Reactions = date_of_Reactions;

    }
}
