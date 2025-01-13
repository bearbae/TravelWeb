package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.ArticleDTO;
import com.bearcode.travelweb.DTO.FlightDTO;
import com.bearcode.travelweb.models.Article;
import com.bearcode.travelweb.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ArticleService {
//    String addArticle(ArticleDTO articleDTO);
    ResponseEntity<String> createArticle(String title, String content1, String content2, String category, String nameAuthor,
                                         String aboutBlog,LocalDate timePost, MultipartFile multipartFile1, MultipartFile multipartFile2,
                                        MultipartFile multipartFile3, MultipartFile multipartFile4);

    // tim bai viet theo
    Page<Article> findAllArticleForSearch(String title, String category, Pageable pageable);
//    // lấy danh sách bai viet
    ArrayList<Article> getAllArticleByTitle(String title);
    ArrayList<Article> getAllArticle();

    // lay 1 so bai viet
    List<Article> findArticleBetween(Long idStart, Long idEnd);
//
    // sửa thông tin bai viet
    Article updateArticle(Long id,String title, String content1,
                          String content2, String category,
                          String nameAuthor,
                          String aboutBlog, LocalDate timePost,
                          MultipartFile multipartFile1,
                          MultipartFile multipartFile2,
                          MultipartFile multipartFile3);
//
//    // xóa thông tin bai viet
    String deleteArticle(Long id);
//
//    // Lấy thông tin 1 bai viet
    Article findArticleById(Long id);

    Article saveArticle(Article article);



}
