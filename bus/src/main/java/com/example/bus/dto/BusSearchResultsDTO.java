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
public class BusSearchResultsDTO {

	@JsonProperty("BusJourneyId")
	Long busJourneyId;
	
	@JsonProperty("TravellerName")
	String travellerName;
	
	@JsonProperty("FromStation")
	String fromStation;
	
	@JsonProperty("ToStation")
	String toStation;
	
	@JsonProperty("DepartureDate")
	String departureDate;
	
	@JsonProperty("ArrivalDate")
	String arrivalDate;
	
	@JsonProperty("IsAcBus")
	String isAcBus;
	
}
