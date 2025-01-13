package com.bearcode.travelweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightBookingRequestDTO {
    private Long flightId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Long userId;
    private int seatCount;
}
