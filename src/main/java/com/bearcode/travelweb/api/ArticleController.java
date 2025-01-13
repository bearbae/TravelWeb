package com.bearcode.travelweb.api;



import com.bearcode.travelweb.models.Article;
import com.bearcode.travelweb.repositories.ArticleRepository;
import com.bearcode.travelweb.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/article")
public class ArticleController {
    private final String UPLOAD_DIR = "src/main/resources/static/uploadsArticle/";
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping("/add")
    public ResponseEntity<String> uploadImageAndCreateArticle(
            @RequestParam("title") String title,
            @RequestParam("content1") String content1,
            @RequestParam("content2") String content2,
            @RequestParam("category") String category,
            @RequestParam("nameAuthor") String nameAuthor,
            @RequestParam("aboutBlog") String aboutBlog,
            @RequestParam("timePost") LocalDate timePost,
            @RequestParam("image1") MultipartFile multipartFile1,
            @RequestParam("image2") MultipartFile multipartFile2,
            @RequestParam("image3") MultipartFile multipartFile3,
            @RequestParam("image4") MultipartFile multipartFile4
            ) {
        return articleService.createArticle(title, content1, content2, category, nameAuthor, aboutBlog, timePost,
                                            multipartFile1, multipartFile2, multipartFile3, multipartFile4);
    }

    @GetMapping("/searchForUser")
    public Page<Article> findAllArticleForSearch(@RequestParam("titleArticle") String title,
                                              @RequestParam("category") String category,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page, limit);
        return articleService.findAllArticleForSearch(title, category , pageable);
    }
    // admin
    @GetMapping("/all")
    public ArrayList<Article> getAllArticle(){

        return articleService.getAllArticle();
    }
    @GetMapping("/1to6")
    public List<Article> getArticleByIdBetWeen(@RequestParam(defaultValue = "95") Long id1,
                                       @RequestParam(defaultValue = "101") Long id2){

        return articleService.findArticleBetween(id1,id2);
    }
    // user
    @GetMapping("/allForUser")
    public Page<Article> getAllArticlehasPage(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int limit){
        Pageable pageable = PageRequest.of(page,limit);
        return articleRepository.findAll(pageable);
    }
    @GetMapping("/search")
    public ArrayList<Article> getAllArticleByTitle(@RequestParam("title") String title){

        return articleService.getAllArticleByTitle(title);
    }

    @PutMapping("/update/{id}")
    public Article updateArticle(@PathVariable Long id,
                                 @RequestParam("title") String title,
                                 @RequestParam("content1") String content1,
                                 @RequestParam("content2") String content2,
                                 @RequestParam("category") String category,
                                 @RequestParam("nameAuthor") String nameAuthor,
                                 @RequestParam("aboutBlog") String aboutBlog,
                                 @RequestParam("timePost") LocalDate timePost,
                                 @RequestParam("image1") MultipartFile multipartFile1,
                                 @RequestParam("image2") MultipartFile multipartFile2,
                                 @RequestParam("image3") MultipartFile multipartFile3){
      return articleService.updateArticle(id, title, content1, content2, category, nameAuthor,
              aboutBlog, timePost, multipartFile1, multipartFile2, multipartFile3);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id){
        String response = articleService.deleteArticle(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id){
        return articleService.findArticleById(id);
    }


}
