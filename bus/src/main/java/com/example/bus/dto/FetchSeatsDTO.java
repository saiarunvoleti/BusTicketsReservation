package com.example.bus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FetchSeatsDTO {

	@JsonProperty("SeatBookingId")
	Long seatBookingId;
	
	@JsonProperty("SeatId")
	Long seatId;
	
	@JsonProperty("SeatNumber")
	String seatNumber;
	
	@JsonProperty("IsSeatAvailable")
	String isSeatAvailable;
	
	@JsonProperty("BusJourneyId")
	Long busJourneyId;
	
}
