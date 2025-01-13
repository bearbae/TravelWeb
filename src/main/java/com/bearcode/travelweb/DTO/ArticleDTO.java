package com.bearcode.travelweb.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String image;
}
