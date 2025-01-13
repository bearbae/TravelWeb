package com.bearcode.travelweb.services.crawlData;

import com.bearcode.travelweb.models.Article;
import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.ArticleRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class ArticleCrawlService {
    @Autowired
    private ArticleRepository articleRepository;

    public void crawlAndSaveData() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        ArrayList<Article> listArticle = new ArrayList<>();
        try {
            String url = "https://phuot3mien.com/danh-muc/an-uong";
            driver.get(url);

            String pageSource = driver.getPageSource();

            Document document = Jsoup.parse(pageSource);
            Elements titles = document.select("h2.post-box-title");
            Elements aboutBlogs = document.select("div.entry");
            Elements Posts = document.select("p.post-meta");

            int totalElements = Math.min(titles.size(), Math.min(aboutBlogs.size(), Posts.size()));
            for (int i = 0; i < totalElements; i++) {
                Article article = new Article();

                Element post = Posts.get(i);
                Element author = post.select("span").get(0);
                Element postTime = post.select("span").get(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                LocalDate date = LocalDate.parse(postTime.text(), formatter);
                article.setTitle(titles.get(i).text());
                article.setAboutBlog(aboutBlogs.get(i).text());
                article.setTimePost(date);
                article.setNameAuthor(author.text());
                article.setCategory("Ẩm thực");
                listArticle.add(article);
            }
            articleRepository.saveAll(listArticle);

//            for(Article a: listArticle){
//                System.out.println(a);
//            }

        }catch (Exception e) {
        e.printStackTrace();
    } finally {
        driver.quit();
    }
    }

}
