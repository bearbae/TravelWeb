package com.bearcode.travelweb.services.crawlData;


import com.bearcode.travelweb.models.Hotel;
import com.bearcode.travelweb.repositories.HotelRepository;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

@Service
public class HotelCrawlService {

    @Autowired
    private HotelRepository hotelRepository;

    public void crawlAndSaveData() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);


        ArrayList<Hotel> listHotel = new ArrayList<>() ;

        try {
            String url = "https://vietgoing.com/hotel/city-41-ho-chi-minh.html?checkin=20%2F12%2F2024&checkout=21%2F12%2F2024&utm_web=118";
            driver.get(url);


            String pageSource = driver.getPageSource();

            Document document = Jsoup.parse(pageSource);
            Elements names = document.select("h4.service-title a");
            Elements locations = document.select("p[class=service-location]");
            Elements prices = document.select("p.service-price");
            int totalElements = Math.min(names.size(), Math.min(locations.size(), prices.size()));
            for (int i = 0; i < totalElements; i++) {
                Hotel hotel = new Hotel();
                Random random = new Random();
                int randomNumber = random.nextInt(30 - 5 + 1) + 5;
                int price = Integer.parseInt(prices.get(i).text().replaceAll("[^0-9]",""));

                hotel.setPrice(BigDecimal.valueOf(price));
                hotel.setName(names.get(i).text());
                hotel.setLocation(locations.get(i).text());
                hotel.setAvailableRooms(randomNumber);
                listHotel.add(hotel);
            }
            hotelRepository.saveAll(listHotel);
        }  catch (Exception e) {
        e.printStackTrace();
    } finally {
        driver.quit();
    }

    }

}
