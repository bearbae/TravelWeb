package com.bearcode.travelweb.controllers.admin;


import com.bearcode.travelweb.models.Article;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class AdminControllerUrl {
    @GetMapping("")
    public String admin() {
        return "admin/index";
    }
    // Quản lý sân bay
    @GetMapping("/airport")
    public String manageAirport() {
        return "admin/airport";
    }

    @GetMapping("/airport/add")
    public String addAirport() {
        return "admin/add-airport";
    }

    @GetMapping("/airport/update")
    public String editAirport() {
        return "admin/update-airport";
    }
    // qly nha hang
    @GetMapping("/restaurant")
    public String manageRestaurant() {
        return "admin/restaurant";
    }
    @GetMapping("/restaurant/add")
    public String addRestaurant() {
        return "admin/add-restaurant";
    }

    @GetMapping("/restaurant/update")
    public String editRestaurant() {
        return "admin/update-restaurant";
    }

    // qly ban
    @GetMapping("/restaurant/table")
    public String manageTableRestaurant() {
        return "admin/table";
    }
    @GetMapping("/restaurant/table/add")
    public String addTableRestaurant() {
        return "admin/add-table";
    }

    @GetMapping("/restaurant/table/update")
    public String editTableRestaurant() {
        return "admin/update-table";
    }
    // quản lý vé
    @GetMapping("/flight")
    public String manageFlight() {
        return "admin/flight";
    }
    @GetMapping("/flight/add")
    public String addFlight() {
        return "admin/add-flight";
    }

    @GetMapping("/flight/update")
    public String updateFlight() {
        return "admin/update-flight";
    }

    @GetMapping("/flight/blank")
    public String sFlight() {
        return "admin/blank";
    }

    // Quản lý chặng bay (Route)
    @GetMapping("/route")
    public String manageRoute() {
        return "admin/route";
    }

    @GetMapping("/route/add")
    public String addRoute() {
        return "admin/add-route";
    }

    @GetMapping("/route/update")
    public String editRoute() {
        return "admin/update-route";
    }

    // quản lý khách sạn
    @GetMapping("/hotel")
    public String manageHotel() {
        return "admin/hotel";
    }
    @GetMapping("/hotel/add")
    public String addHotel() {
        return "admin/add-hotel";
    }

    @GetMapping("/hotel/update")
    public String editHotel() {
        return "admin/update-hotel";
    }

    // quản lý phong
    @GetMapping("/hotel/room")
    public String manageRoom() {
        return "admin/room";
    }
    @GetMapping("/hotel/room/add")
    public String addRoom() {
        return "admin/add-room";
    }

    @GetMapping("/hotel/room/update")
    public String editRoom() {
        return "admin/update-room";
    }

    // quản lý bài viết
    @GetMapping("/article")
    public String manageArticle() {
        return "admin/article";
    }
    @GetMapping("/article/add")
    public String addArticle(Model model) {
        model.addAttribute("article", new Article());
        return "admin/add-article";
    }
    @GetMapping("/article/update")
    public String editArticle() {
        return "admin/update-article";
    }


    // quản lý tour du lịch
    @GetMapping("/tour")
    public String manageTour() {
        return "admin/tour";
    }
    @GetMapping("/tour/add")
    public String addTour() {
        return "admin/add-tour";
    }
    @GetMapping("/tour/update")
    public String editTour() {
        return "admin/update-tour";
    }
    @GetMapping("/tour/image")
    public String manageImageOfTour() {
        return "admin/imageOfTour";
    }
    @GetMapping("/tour/image/add")
    public String addImageOfTour() {
        return "admin/add-image";
    }
    @GetMapping("/tour/image/update")
    public String editImageOfTour() {
        return "admin/update-image";
    }
    @GetMapping("/tour/serviceTour")
    public String manageServiceTour() {
        return "admin/ServiceDetails";
    }
    @GetMapping("/tour/serviceTour/add")
    public String addServiceTour() {
        return "admin/add-ServiceTour";
    }
    @GetMapping("/tour/serviceTour/update")
    public String editServiceTour() {
        return "admin/update-ServiceTour";
    }

}
