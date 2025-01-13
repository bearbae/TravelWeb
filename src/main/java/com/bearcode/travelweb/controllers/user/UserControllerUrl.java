package com.bearcode.travelweb.controllers.user;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserControllerUrl {
    // quản lý khách sạn
    @GetMapping("/hotel")
    public String viewHotel() {
        return "user/hotel";
    }
    @GetMapping("/room")
    public String viewRoom() {
        return "user/room";
    }
    @GetMapping("/article")
    public String viewArticle() {
        return "user/article";
    }
    @GetMapping("/articleDetail")
    public String viewArticleDetail() {
        return "user/articleDetail";
    }
    @GetMapping("/home")
    public String viewHome() {
        return "user/home";
    }
    @GetMapping("/servicePackage")
    public String viewService() { return "user/servicePackage";}
    @GetMapping("/allTour")
    public String viewAllTour() { return "user/viewAllTour";}
    @GetMapping("/listForAddress")
    public String listForAddress() {return  "user/listForAddress" ; }
    @GetMapping("/userInfo")
    public String Userinfor(){ return "user/userInfo";}
// nha hang
    @GetMapping("/restaurant")
    public String Restaurant(){ return "user/restaurant";}
    @GetMapping("/table")
    public String table(){ return "user/table";}
    // maybay
    @GetMapping("/flight")
    public String Flight(){ return "user/ticket_flight";}
    @GetMapping("/book_ticket_flight")
    public String bookTicketFlight() { return  "user/book_ticket_flight";}
    @GetMapping("/book_ticket_flight_return")
    public String bookTicketFlightReturn() { return  "user/book_ticket_flight_return";}
}
