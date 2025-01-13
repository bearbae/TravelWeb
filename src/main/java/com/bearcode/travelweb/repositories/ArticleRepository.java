package com.bearcode.travelweb.repositories;

import com.bearcode.travelweb.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    Boolean existsByTitle(String title);

    List<Article> findByIdBetween(Long idStart, Long idEnd);
    Optional<Article> getArticleById(Long id);

    Article findArticleById(Long id);

    ArrayList<Article> findByTitleContainingIgnoreCase(String title);
}
