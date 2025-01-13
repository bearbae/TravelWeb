package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.Hotel;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class HotelSpecification {
    public static Specification<Hotel> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                location == null || location.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }
    public static Specification<Hotel> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Hotel> haspriceHotel(String priceHotel) {
        return (root, query, criteriaBuilder) -> {
            if (priceHotel == null || priceHotel.isEmpty() || priceHotel.equals("allPrice")) {
                return criteriaBuilder.conjunction();
            }

            switch (priceHotel) {
                case "underPrice":
                    return criteriaBuilder.lessThan(root.get("price"), new BigDecimal(1000000));
                case "middlePrice":
                    return criteriaBuilder.between(root.get("price"), new BigDecimal(1000000), new BigDecimal(3000000));
                case "abovePrice":
                    return criteriaBuilder.greaterThan(root.get("price"), new BigDecimal(3000000));
                default:
                    return criteriaBuilder.conjunction();
            }
        };
    }
}
