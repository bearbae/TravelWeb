package com.bearcode.travelweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private BigDecimal price;
    private int availableTables;
    private String imageRestaurant;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.REMOVE)
    private List<TableRestaurant> tableRestaurants;
}
