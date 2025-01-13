package com.bearcode.travelweb.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageOfTour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private String img5;
    @OneToOne()
    @JoinColumn(name = "tour_id")
    @JsonIgnore
    private Tour tour;
}
