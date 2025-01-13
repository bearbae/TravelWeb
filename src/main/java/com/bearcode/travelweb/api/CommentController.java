package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.Comment;
import com.bearcode.travelweb.repositories.CommentRepository;
import com.bearcode.travelweb.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentRepository commentRepository;
    @PostMapping("/addComment/idArticle/{id}")
    public ResponseEntity<String> CreateComment(@PathVariable Long id, @RequestParam("nameUser") String nameUser,
                                                @RequestParam("comment") String comment) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        Comment c = new Comment();
        c.setNameUser(nameUser);
        c.setComment(comment);
        c.setDate(date);
        c.setTime(time);
        c.setArticle(articleService.findArticleById(id));
        commentRepository.save(c);
        return ResponseEntity.status(HttpStatus.OK).body("Them comment thanh cong");
    }
    @GetMapping("/all/idArticle/{id}")
    public List<Comment> getAllComment(@PathVariable Long id){
        return commentRepository.getAllByArticle_Id(id);
    }

}
