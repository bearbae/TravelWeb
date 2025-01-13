package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoundTripBookingRequestDTO {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String specialRequest;
    private Long departureFlightId;
    private Long returnFlightId;
    private Long userId;
    private int seatCount;
}
