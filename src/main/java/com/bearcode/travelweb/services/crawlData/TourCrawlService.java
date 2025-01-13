package com.bearcode.travelweb.services.crawlData;

import com.bearcode.travelweb.models.Tour;
import com.bearcode.travelweb.repositories.TourRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class TourCrawlService {
    @Autowired
    private TourRepository tourRepository ;
    public void crawlAndSaveData()  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        ArrayList<Tour> listTour = new ArrayList<>();
        try {
            String url = "https://www.klook.com/vi/popular/all/?spm=Home.Popular%3Aany%3A%3APopularActivities%3AViewMoreBtn&clickId=dfa201a257";
            driver.get(url);

            // Lấy mã HTML của trang web sau khi đã tải xong
            String pageSource = driver.getPageSource();

            // Sử dụng Jsoup để phân tích mã HTML
            Document document = Jsoup.parse(pageSource);
            // Lấy dữ liệu từ trang đã cập nhật
            Elements typeTourElements = document.select("div.card-subText");
            Elements titleTourElements = document.select("div.card-title");
            Elements rateStartElements = document.select("span.review-star");
            Elements quantityCommentElements = document.select("span.review-number");
            Elements bookedElements = document.select("span.review-booked");
            Elements priceElements = document.select("span.price-number");

            int totalElements = Math.min(typeTourElements.size(),
                                Math.min(titleTourElements.size(),
                                Math.min(rateStartElements.size(),
                                Math.min(quantityCommentElements.size(),
                                Math.min(bookedElements.size(), priceElements.size())))));
            for (int i = 0; i < totalElements; i++) {
                Tour tour = new Tour();

                // Lấy chuỗi từ typeTourElements và tách thành 2 phần
                String[] typeAndAddress = typeTourElements.get(i).text().split("•");

                // Gán giá trị cho typeTour và address sau khi tách
                String typeTour = typeAndAddress[0].trim(); // Phần trước "•"
                String address = typeAndAddress.length > 1 ? typeAndAddress[1].trim() : ""; // Phần sau "•" (nếu có)
                String rateStartText = rateStartElements.get(i).text();
                Double starRating = Double.parseDouble(rateStartText.replaceAll("★", "").trim());
                int comment = Integer.parseInt(quantityCommentElements.get(i).text().replaceAll("[(),]", ""));
                int booked = Integer.parseInt(bookedElements.get(i).text().replaceAll("[^0-9]", ""));
                int price = Integer.parseInt(priceElements.get(i).text().replaceAll("[^0-9]",""));

                tour.setTypeTour(typeTour);
                tour.setAddress(address);
                tour.setTitleTour(titleTourElements.get(i).text());
                tour.setRateStar(starRating);
                tour.setQuantityComment(comment);
                tour.setBooked(booked);
                tour.setPrice(BigDecimal.valueOf(price));
                listTour.add(tour);
            }
            tourRepository.saveAll(listTour);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
