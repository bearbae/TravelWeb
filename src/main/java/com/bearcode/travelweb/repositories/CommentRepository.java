package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByArticle_Id(Long id);
}
