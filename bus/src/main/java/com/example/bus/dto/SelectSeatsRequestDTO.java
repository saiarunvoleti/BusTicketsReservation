package com.example.bus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectSeatsRequestDTO {

	@JsonProperty("SeatBookingId")
	@NotNull
	Long seatBookingId;
	
	@JsonProperty("BusJourneyId")
	@NotNull
	Long busJourneyId;
	
	@JsonProperty("SeatId")
	@NotNull
	Long seatId;
	
	@JsonProperty("PassengerName")
	@NotNull
	String passengerName;
	
	@JsonProperty("PassengerGender")
	@NotNull
	String passengerGender;
	
	@JsonProperty("PassengerAge")
	@NotNull
	Long passengerAge;
	
}
