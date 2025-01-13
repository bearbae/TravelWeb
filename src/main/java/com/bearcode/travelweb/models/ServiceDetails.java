package com.bearcode.travelweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime timeToGo;
    private LocalTime timeToBack;
    private LocalTime time1;
    private String address1;
    private LocalTime time2;
    private String address2;
    private LocalTime time3;
    private String address3;
    private String vehicle;
    private BigDecimal pricePerAdult;
    private BigDecimal pricePerChild;
    private String code;

    @ManyToOne()
    @JoinColumn(name = "tour_id")
    @JsonIgnore
    private Tour tour;
}
