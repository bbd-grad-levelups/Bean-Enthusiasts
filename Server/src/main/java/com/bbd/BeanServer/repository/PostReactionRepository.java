package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.PostReaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {
    @Query("SELECT COALESCE(SUM(CASE WHEN r.reaction_type_id = 1 THEN 1 ELSE -1 END), 0) " +
            "FROM PostReaction pr " +
            "JOIN Reaction r ON pr.reaction_id = r.reaction_id " +
            "WHERE pr.post_id = :post_id")
    int getReactionCountForPost(@Param("post_id") Long post_id);

}
