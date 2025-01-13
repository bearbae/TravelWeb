package com.bearcode.travelweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content1;
    @Column(columnDefinition = "TEXT")
    private String content2;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String category;
    private String nameAuthor;
    @Column(columnDefinition = "TEXT")
    private String aboutBlog;
    private LocalDate timePost;
}
