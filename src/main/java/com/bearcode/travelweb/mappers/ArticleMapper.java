package com.bearcode.travelweb.mappers;

import com.bearcode.travelweb.DTO.ArticleDTO;
import com.bearcode.travelweb.models.Article;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    private ModelMapper modelMapper;

    public ArticleMapper(){
        this.modelMapper = new ModelMapper();
    }

    public Article convertToEntity(ArticleDTO articleDTO){ return modelMapper.map(articleDTO, Article.class);}

    public ArticleDTO convertToDTO(Article article){ return modelMapper.map(article, ArticleDTO.class) ; }


}
