package com.bearcode.travelweb.api;

import com.bearcode.travelweb.models.Flight;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@CrossOrigin(origins = "http://127.0.0.1:5500")

public class FlightSpecification {

    public static Specification<Flight> hasDepartureDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("departureDate"), date);
    }



    public static Specification<Flight> hasDepartureCity(String departureCity) {
        return (root, query, criteriaBuilder) -> {
            if (departureCity == null || departureCity.isEmpty()) {
                return criteriaBuilder.conjunction(); // Không lọc nếu không có thành phố
            }
            // Sử dụng like và lower để không phân biệt chữ hoa/chữ thường
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("departureCity")),
                    "%" + departureCity.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Flight> hasArrivalCity(String arrivalCity) {
        return (root, query, criteriaBuilder) -> {
            if (arrivalCity == null || arrivalCity.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("arrivalCity")),
                    "%" + arrivalCity.toLowerCase() + "%"
            );
        };
    }


    public static Specification<Flight> hasDepartureDate(LocalDate departureDate) {
        return (root, query, criteriaBuilder) -> {
            if (departureDate == null) {
                return criteriaBuilder.conjunction(); // Không lọc nếu không có ngày
            }
            return criteriaBuilder.equal(root.get("departureDate"), departureDate);
        };
    }
}
