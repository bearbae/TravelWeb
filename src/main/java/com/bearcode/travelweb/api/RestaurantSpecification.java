package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
public class RestaurantSpecification {
    public static Specification<Restaurant> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                location == null || location.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }
    public static Specification<Restaurant> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name == null || name.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Restaurant> haspriceRestaurant(String priceRestaurant) {
        return (root, query, criteriaBuilder) -> {
            if (priceRestaurant == null || priceRestaurant.isEmpty() || priceRestaurant.equals("allPrice")) {
                return criteriaBuilder.conjunction();
            }

            switch (priceRestaurant) {
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
