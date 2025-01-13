package com.bearcode.travelweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeTour;
    private String address;
    private String titleTour;
    private Double rateStar;
    private int quantityComment;
    private int booked;
    private BigDecimal price;
    @Column(length = 1000)
    private String highlight;
    @Column(columnDefinition = "TEXT")
    private String content;
    @OneToOne(mappedBy = "tour", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private ImageOfTour imageOfTour;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ServiceDetails> serviceDetails;
}
