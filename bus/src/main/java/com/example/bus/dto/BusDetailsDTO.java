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
public class BusDetailsDTO {

	@JsonProperty("BusNumber")
	String busNumber;
	
	@JsonProperty("IsACBus")
	String isACBus;
	
	@JsonProperty("Seats")
	List<String> seats;
}
