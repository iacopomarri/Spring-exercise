package com.ergon.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ergon.exercise.model.Comment;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
