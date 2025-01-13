package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.api.ArticleSpecification;
import com.bearcode.travelweb.mappers.ArticleMapper;
import com.bearcode.travelweb.models.Article;
import com.bearcode.travelweb.repositories.ArticleRepository;
import com.bearcode.travelweb.services.ArticleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final String UPLOAD_DIR = "src/main/resources/static/uploadsArticle/";

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseEntity<String> createArticle(String title, String content1,
                                                String content2, String category,
                                                String nameAuthor,
                                                String aboutBlog, LocalDate timePost,
                                                MultipartFile multipartFile1,
                                                MultipartFile multipartFile2,
                                                MultipartFile multipartFile3,
                                                MultipartFile multipartFile4) {
        if (multipartFile1.isEmpty() || multipartFile2.isEmpty() || multipartFile3.isEmpty() ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File ảnh không được để trống");
        }
        if(articleRepository.existsByTitle(title)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bài viết đã tồn tại");
        } else {
            try {
                String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
                String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
                String fileName3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());
                String fileName4 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());

                Path uploadPath1 = Paths.get(UPLOAD_DIR + fileName1);
                Path uploadPath2 = Paths.get(UPLOAD_DIR + fileName2);
                Path uploadPath3 = Paths.get(UPLOAD_DIR + fileName3);
                Path uploadPath4 = Paths.get(UPLOAD_DIR + fileName4);
                if (!Files.exists(uploadPath1.getParent())) {
                    Files.createDirectories(uploadPath1.getParent());
                }
                if (!Files.exists(uploadPath2.getParent())) {
                    Files.createDirectories(uploadPath2.getParent());
                }
                if (!Files.exists(uploadPath3.getParent())) {
                    Files.createDirectories(uploadPath3.getParent());
                }
                if (!Files.exists(uploadPath4.getParent())) {
                    Files.createDirectories(uploadPath4.getParent());
                }

                // Lưu ảnh vào thư mục
                Files.copy(multipartFile1.getInputStream(), uploadPath1, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(multipartFile2.getInputStream(), uploadPath2, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(multipartFile3.getInputStream(), uploadPath3, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(multipartFile4.getInputStream(), uploadPath4, StandardCopyOption.REPLACE_EXISTING);

                // Tạo mới bài viết
                Article article = new Article();
                article.setTitle(title);
                article.setContent1(content1);
                article.setContent2(content2);
                article.setCategory(category);
                article.setNameAuthor(nameAuthor);
                article.setAboutBlog(aboutBlog);
                article.setTimePost(timePost);
                article.setImage1(fileName1);
                article.setImage2(fileName2);
                article.setImage3(fileName3);

                articleRepository.save(article);

                return ResponseEntity.ok("Bài viết đã được tạo thành công với ID: " + article.getId());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi upload file: " + e.getMessage());
            }
        }
    }

    @Override
    public Page<Article> findAllArticleForSearch(String title, String category, Pageable pageable) {
        Specification<Article> spec = Specification.where(ArticleSpecification.hasTitle(title))
                .and(ArticleSpecification.hasCategory(category));
        return articleRepository.findAll(spec, pageable);
    }

    @Override
    public ArrayList<Article> getAllArticleByTitle(String title) {
            return articleRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public ArrayList<Article> getAllArticle() {
        return new ArrayList<>(articleRepository.findAll());
    }

    @Override
    public List<Article> findArticleBetween(Long idStart, Long idEnd) {
        return articleRepository.findByIdBetween(idStart,idEnd);
    }

    @Override
    public Article updateArticle(Long id, String title, String content1,
                                 String content2, String category,
                                 String nameAuthor,
                                 String aboutBlog, LocalDate timePost,
                                 MultipartFile multipartFile1,
                                 MultipartFile multipartFile2,
                                 MultipartFile multipartFile3) {
        Optional<Article> optionalArticle = articleRepository.getArticleById(id);
        if(!optionalArticle.isPresent()){
            throw new EntityNotFoundException("khong tim thay article voi id: "+ id);
        }
        else {
            if (multipartFile1.isEmpty() || multipartFile2.isEmpty() || multipartFile3.isEmpty() ) {
                throw new Error("File không được để trống");
            }
            try {
                String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
                String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());
                String fileName3 = StringUtils.cleanPath(multipartFile3.getOriginalFilename());

                Path uploadPath1 = Paths.get(UPLOAD_DIR + fileName1);
                Path uploadPath2 = Paths.get(UPLOAD_DIR + fileName2);
                Path uploadPath3 = Paths.get(UPLOAD_DIR + fileName3);
                if (!Files.exists(uploadPath1.getParent())) {
                    Files.createDirectories(uploadPath1.getParent());
                }
                if (!Files.exists(uploadPath2.getParent())) {
                    Files.createDirectories(uploadPath2.getParent());
                }
                if (!Files.exists(uploadPath3.getParent())) {
                    Files.createDirectories(uploadPath3.getParent());
                }

                // Lưu ảnh vào thư mục
                Files.copy(multipartFile1.getInputStream(), uploadPath1, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(multipartFile2.getInputStream(), uploadPath2, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(multipartFile3.getInputStream(), uploadPath3, StandardCopyOption.REPLACE_EXISTING);

                // Tạo mới bài viết
                Article article = optionalArticle.get();
                article.setTitle(title);
                article.setContent1(content1);
                article.setContent2(content2);
                article.setCategory(category);
                article.setNameAuthor(nameAuthor);
                article.setAboutBlog(aboutBlog);
                article.setTimePost(timePost);
                article.setImage1(fileName1);
                article.setImage2(fileName2);
                article.setImage3(fileName3);
                articleRepository.save(article);
                return article;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }


    @Override
    public String deleteArticle(Long id) {
        Optional<Article> articleOptional = articleRepository.getArticleById(id);
        if(!articleOptional.isPresent()){
            return "Bài viết này không tồn tại!";
        }
        articleRepository.deleteById(id);
        return "Xóa bài viết thành công.";
    }

    @Override
    public Article findArticleById(Long id) {
        Optional<Article> articleOptional = articleRepository.getArticleById(id);
        Article article = null ;
        if(articleOptional.isPresent()){
            article = articleOptional.get();
        } else{
            throw new RuntimeException("Khong tim thay Article voiw id:"+ id);
        }
        return article;
    }

    @Override
    public Article saveArticle(Article article) {
        return articleRepository.save(article);
    }
}
