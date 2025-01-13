package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.Article;
import org.springframework.data.jpa.domain.Specification;


public class ArticleSpecification {
    public static Specification<Article> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title == null || title.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Article> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%");
        };
    }
}
