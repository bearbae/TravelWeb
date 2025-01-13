package com.bearcode.travelweb.services.crawlData;

import com.bearcode.travelweb.models.Room;
import com.bearcode.travelweb.repositories.RoomRepository;
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
import java.util.Random;

@Service
public class RoomCrawlService {

    @Autowired
    private RoomRepository roomRepository;

    public void crawlAndSaveData() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);


        ArrayList<Room> listRoom = new ArrayList<>();

        try {
            String url = "https://vietgoing.com/hotel/1583-queen-ann-hotel-sai-gon.html?utm_web=24";
            driver.get(url);

            String pageSource = driver.getPageSource();

            Document document = Jsoup.parse(pageSource);
            Elements names = document.select("span.text_link");
//            Elements roomtypes = document.select("p[class=room_attr_hot]");
//            Elements views = document.select("p[class=room_attr_hot]");
//            Elements areas = document.select("p[class=room_attr_hot]");
            Elements prices = document.select("div[class=price-wrapper]");
            Elements roomAttributes = document.select("p[class=room_attr_hot]");

//            System.out.println(names);
//            System.out.println(prices);
//            System.out.println(roomAttributes);

            for (int i = 0; i < names.size(); i++) {
                Room room = new Room();
                room.setRoomName(names.get(i).text());
                Random random = new Random();
                int randomNumber = random.nextInt(15 - 1 + 1) + 1;
                room.setQuantity(randomNumber);

//                int attrIndex = i * 5;
//                if (attrIndex < roomAttributes.size()) {
//                    room.setView(roomAttributes.get(attrIndex).text());
//                    room.setArea(Integer.parseInt(roomAttributes.get(attrIndex + 1).text().split("m")[0].replaceAll("[^0-9]","")));
//                    room.setRoomType(roomAttributes.get(attrIndex + 2).text());
//                    room.setAmenities(roomAttributes.get(attrIndex + 3).text());
//                }
//
                    int attrIndex = i * 4;
                    if (attrIndex < roomAttributes.size()) {
                        room.setArea(Integer.parseInt(roomAttributes.get(attrIndex).text().split("m")[0].replaceAll("[^0-9]","")));
                        room.setRoomType(roomAttributes.get(attrIndex + 1).text());
                        room.setAmenities(roomAttributes.get(attrIndex + 2).text());
                    }
                if (i < prices.size()) {
                    int price = Integer.parseInt(prices.get(i).text().replaceAll("[^0-9]",""));
                    room.setPricePerNight(BigDecimal.valueOf(price));
                }
                listRoom.add(room);
            }
            roomRepository.saveAll(listRoom);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
