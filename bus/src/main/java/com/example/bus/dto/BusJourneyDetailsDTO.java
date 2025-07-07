package com.example.bus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusJourneyDetailsDTO {

	@JsonProperty("BusNumber")
	String busNumber;
	
	@JsonProperty("FromStation")
	String fromStation;
	
	@JsonProperty("ToStation")
	String toStation;
	
	@JsonProperty("FromDate")
	String departureDate;
	
	@JsonProperty("ToDate")
	String arrivalDate;
	
	@JsonProperty("TicketPrice")
	String ticketPrice;
	
}
