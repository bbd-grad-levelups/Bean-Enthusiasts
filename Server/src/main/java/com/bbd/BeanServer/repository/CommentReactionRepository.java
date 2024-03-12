package com.bbd.BeanServer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bbd.shared.models.CommentReaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    @Query("SELECT COALESCE(SUM(CASE WHEN r.reaction_type_id = 1 THEN 1 ELSE -1 END), 0) " +
            "FROM CommentReaction cr " +
            "JOIN Reaction r ON cr.reaction_id = r.reaction_id " +
            "WHERE cr.comment_id = :comment_id")
    int getReactionCountForComment(@Param("comment_id") Long comment_id);
}
