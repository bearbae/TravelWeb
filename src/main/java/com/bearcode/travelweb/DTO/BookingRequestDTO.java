package com.bearcode.travelweb.DTO;

import com.bearcode.travelweb.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequestDTO {
    private LocalDate checkInDate;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String specialRequest;
    private BigDecimal totalAmount;
    private Map<Long, Integer> rooms;
    private User user;
}
